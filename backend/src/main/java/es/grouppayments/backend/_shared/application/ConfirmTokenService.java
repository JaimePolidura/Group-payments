package es.grouppayments.backend._shared.application;

import es.grouppayments.backend._shared.domain.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class ConfirmTokenService {
    private final TokenService tokenService;

    public String generate(UUID userId, ConfirmationAction action){
        return this.tokenService.generateToken(
                userId.toString(),
                new HashMap<>() {{put("action", action);}},
                Date.from(ZonedDateTime.now().plusMinutes(2).toInstant())
        );
    }

    public String generate(UUID userId, ConfirmationAction action, String extra){
        return this.tokenService.generateToken(
                userId.toString(),
                new HashMap<>() {{
                    put("action", action);
                    put("extra", extra);
                }},
                Date.from(ZonedDateTime.now().plusMinutes(2).toInstant())
        );
    }

    public UUID getUserId(String token){
        return UUID.fromString(this.tokenService.getBody(token));
    }

    public boolean isExpired(String token){
        return this.tokenService.isExpired(token);
    }

    public String getExtra(String token){
        return this.tokenService.getOther(token, "extra");
    }

    public ConfirmationAction getAction(String token){
        return ConfirmationAction.valueOf(this.tokenService.getOther(token, "action"));
    }
}
