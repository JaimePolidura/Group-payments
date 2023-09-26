package es.grouppayments.backend.users.users._shared.infrastructure;

import es.grouppayments.backend._shared.domain.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class AuthenticationJWTService {
    private final TokenService tokenService;

    public String generate(UUID userId){
        return tokenService.generateToken(userId.toString(), Date.from(ZonedDateTime.now().plusDays(1).toInstant()));
    }

    public UUID getUserId(String token){
        return UUID.fromString(this.tokenService.getBody(token));
    }

    public boolean isValid(String token, UUID userId){
        return tokenService.isBodyValid(token, userId.toString());
    }

    public boolean isExpired (String token) {
        return this.tokenService.isExpired(token);
    }

    public boolean containsNecesaryData(String token){
        return getUserId(token) != null;
    }
}
