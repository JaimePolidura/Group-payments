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

    public void save(String username, String email) {
        userRepository.save(new User(UUID.randomUUID(), username, email, LocalDateTime.now()));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findByUserId(UUID id) {
        return this.userRepository.findByUserId(id);
    }
}
