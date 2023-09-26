package es.grouppayments.backend._shared.infrastructure.auth;

import es.grouppayments.backend._shared.domain.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public final class JWTokenService implements TokenService {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    @Override
    public String generateToken(String body, Map<String, Object> other, Date expiration) {
        return Jwts.builder()
                .setClaims(other)
                .setSubject(body)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(expiration)
                .compact();
    }

    @Override
    public String generateToken(String subject, Date expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(expiration)
                .compact();
    }

    @Override
    public String getBody(String token) {
        return this.getClaims(token).getSubject();
    }

    @Override
    public boolean isExpired (String token) {
        return this.getClaims(token).getExpiration().before(new Date());
    }

    @Override
    public String getOther(String token, String key) {
        return this.getClaims(token)
                .get(key)
                .toString();
    }

    @Override
    public boolean isBodyValid(String token, String expectedBody) {
        Try<Boolean> validTry = Try.of(() -> expectedBody.equals(this.getBody(token)));

        return validTry.isSuccess() && validTry.get();
    }

    @Override
    public boolean hasBody(String token) {
        return getBody(token) != null;
    }

    private Claims getClaims (String token){
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }


}
