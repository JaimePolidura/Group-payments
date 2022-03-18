package es.grouppayments.backend._shared.infrastructure.auth;


import es.grouppayments.backend.users._shared.domain.UserState;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JWTUtils {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    public String generateToken(UUID userId, UserState state) {
        return Jwts.builder()
                .setSubject(GroupPaymentsJwtSubject.toJSONString(userId, state))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
                .compact();
    }

    public boolean isValid (String token, UUID userId) {
        Try<Boolean> validTry = Try.of(() -> userId.equals(getUserId(token)));

        return validTry.isSuccess() && validTry.get();
    }

    public UUID getUserId(String token) {
        return UUID.fromString(String.valueOf(new JSONObject(decryptToken(token)).get("userId")));
    }

    public UserState getUserState(String token){
        return UserState.valueOf(String.valueOf(new JSONObject(decryptToken(token)).get("userState")));
    }

    public boolean isExpired (String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public boolean containsNecesaryData(String token){
        return getUserState(token) != null && getUserId(token) != null;
    }

    private String decryptToken(String token){
        return getClaims(token).getSubject();
    }

    private Claims getClaims (String token){
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    @AllArgsConstructor
    private static class GroupPaymentsJwtSubject {
        @Getter private final UUID userId;
        @Getter private final UserState userState;

        public static String toJSONString(UUID userId, UserState userState){
            return new JSONObject(Map.of(
                    "userId", userId.toString(),
                    "userState", userState.toString()
            )).toString();
        }
    }
}
