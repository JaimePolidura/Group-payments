package _shared.users;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserRepository;
import es.grouppayments.backend.users.users._shared.domain.UserState;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Predicate;

import static es.grouppayments.backend.users.users._shared.domain.Currency.EUR;
import static org.junit.Assert.*;

public interface UsingUsers extends UsingUserImages{
    String DEFAULT_USERNAME = "paco";
    String DEFAULT_EMAIL = "email@email.com";
    int DEFAULT_IMAGE_ID = 1;

    UserRepository usersRepository();

    default void addUser(UUID... userId){
        Arrays.stream(userId).forEach(this::addUser);
    }

    default void addUser(UUID userId){
        this.usersRepository().save(new User(userId, DEFAULT_USERNAME, DEFAULT_EMAIL, LocalDateTime.now(), DEFAULT_IMAGE_ID,
                UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));
    }

    default void addUser(UUID userId, UserState state){
        this.usersRepository().save(new User(userId, DEFAULT_USERNAME, DEFAULT_EMAIL, LocalDateTime.now(), DEFAULT_IMAGE_ID,
                state, "ES", EUR));
    }

    default void addUser(UUID userId, String email){
        this.usersRepository().save(new User(userId, DEFAULT_USERNAME, email, LocalDateTime.now(), DEFAULT_IMAGE_ID,
                UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));
    }

    default void assertUserDeleted(UUID userId){
        assertTrue(this.usersRepository().findByUserId(userId).isEmpty());
    }

    default void assertContentUser(UUID userId, Predicate<User> condition){
        var userToCheck = this.usersRepository().findByUserId(userId)
                .get();

        assertTrue(condition.test(userToCheck));
    }
}
