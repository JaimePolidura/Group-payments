package es.grouppayments.backend.users._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class User extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final LocalDateTime loggedDate;
    @Getter private final String photoUrl;
    @Getter private final UserState state;

    public User updateSignUpState(UserState newState){
        return new User(userId, username, email, loggedDate, photoUrl, newState);
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "userId", userId.toString(),
                "username", username,
                "email", email,
                "photoUrl", photoUrl,
                "loggedDate", loggedDate.toString(),
                "state", state
        );
    }
}
