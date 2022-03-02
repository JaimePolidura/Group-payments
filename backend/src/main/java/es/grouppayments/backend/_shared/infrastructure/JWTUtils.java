package es.grouppayments.backend._shared.infrastructure;


import es.jaime.javaddd.domain.configuration.ApplicationConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@AllArgsConstructor
@Service
public class JWTUtils {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
                .compact();
    }

    public boolean isValid (String token, String username) {
        Try<Boolean> validTry = Try.of(() ->  username.equalsIgnoreCase(getUsername(token)));

        return validTry.isSuccess() && validTry.get();
    }

    public String getUsername (String token) {
        return getClaims(token).getSubject();
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
