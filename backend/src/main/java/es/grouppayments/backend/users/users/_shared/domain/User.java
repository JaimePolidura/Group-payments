package es.grouppayments.backend.users.users._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public final class User extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final LocalDateTime loggedDate;
    @Getter private final int userImageId;
    @Getter private final UserState state;
    @Getter private final String country;
    @Getter private final Currency currency;

    public User withSignUpState(UserState newState){
        return new User(userId, username, email, loggedDate, this.userImageId, newState, country, currency);
    }

    public User withUserImageId(int userImageId){
        return new User(userId, username, email, loggedDate, userImageId, this.state, country, currency);
    }
}
