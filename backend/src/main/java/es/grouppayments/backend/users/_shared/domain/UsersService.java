package es.grouppayments.backend.users._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {
    private final UserRepository userRepository;

    public UUID save(String username, String email) {
        UUID userId = UUID.randomUUID();

        userRepository.save(new User(userId, username, email, LocalDateTime.now()));

        return userId;
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUserId(UUID id) {
        return this.userRepository.findByUserId(id);
    }
}
