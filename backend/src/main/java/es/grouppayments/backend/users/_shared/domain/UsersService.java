package es.grouppayments.backend.users._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {
    private final UserRepository userRepository;

    public User create(String username, String email, String photoUrl, String country) {
        User newUser = new User(UUID.randomUUID(), username, email, LocalDateTime.now(), photoUrl, UserState.SIGNUP_OAUTH_COMPLETED, country);

        userRepository.save(newUser);

        return newUser;
    }

    public void update(User user){
        this.userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUserId(UUID id) {
        return this.userRepository.findByUserId(id);
    }
}
