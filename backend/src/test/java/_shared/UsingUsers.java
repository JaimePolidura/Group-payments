package _shared;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public interface UsingUsers {
    UserRepository usersRepository();

    default void addUser(UUID... userId){
        Arrays.stream(userId).forEach(this::addUser);
    }

    default void addUser(UUID userId){
        this.usersRepository().save(new User(userId, "sa", "jhksa", LocalDateTime.now()));
    }
}
