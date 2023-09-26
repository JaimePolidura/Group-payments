package es.grouppayments.backend.users.users._shared.application;

import es.grouppayments.backend.users.users._shared.domain.*;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {
    private final UserRepository userRepository;
    private final EventBus eventBus;

    public User create(String username, String email, int imagePhotoUrl, String country) {
        Currency currency = Currency.getCurrencyByCountry(country);

        User newUser = new User(UUID.randomUUID(), username, email, LocalDateTime.now(), imagePhotoUrl,
                UserState.SIGNUP_OAUTH_COMPLETED, country, currency);

        userRepository.save(newUser);

        return newUser;
    }

    public void update(User user){
        this.userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found for that email"));
    }

    public User getByUserId(UUID id) {
        return this.userRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFound("User not found for that id"));
    }

    public void deleteByUserId(UUID userId){
        this.userRepository.deleteByUserId(userId);

        this.eventBus.publish(new UserDeleted(userId));
    }
}
