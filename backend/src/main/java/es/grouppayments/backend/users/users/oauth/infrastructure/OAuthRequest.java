package es.grouppayments.backend.users.users.oauth.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class OAuthRequest {
    @Getter private String username;
    @Getter private String token;
}
