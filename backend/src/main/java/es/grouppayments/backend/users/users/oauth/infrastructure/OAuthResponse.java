package es.grouppayments.backend.users.users.oauth.infrastructure;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

public final class OAuthResponse {
    @Getter private final String token;
    @Getter private final UserOAuthResponse user;

    public OAuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserOAuthResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getLoggedDate(),
                user.getUserImageId(), user.getState(), user.getCountry(), new UserCurrencyOAuthResponse(user.getCurrency().name(), user.getCurrency().symbol));
    }

    @AllArgsConstructor
    public static class UserOAuthResponse {
        @Getter private final UUID userId;
        @Getter private final String username;
        @Getter private final String email;
        @Getter private final LocalDateTime loggedDate;
        @Getter private final int userImageId;
        @Getter private final UserState state;
        @Getter private final String country;
        @Getter private final UserCurrencyOAuthResponse currency;
    }

    @AllArgsConstructor
    private static class UserCurrencyOAuthResponse {
        @Getter private final String code;
        @Getter private final String symbol;
    }
}
