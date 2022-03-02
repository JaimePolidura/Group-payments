package es.grouppayments.backend._shared.infrastructure;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class JWTUtils {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    public String generateToken(UUID userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
                .compact();
    }

    public boolean isValid (String token, UUID userId) {
        Try<Boolean> validTry = Try.of(() ->  userId.equals(getUserId(token)));

        return validTry.isSuccess() && validTry.get();
    }

    public UUID getUserId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    public boolean isExpired (String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims (String token){
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
