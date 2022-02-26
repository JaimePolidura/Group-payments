package es.grouppayments.backend.users._shared.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsersService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<User> findByUserId(UUID id) {
        return this.userRepository.findByUserId(id);
    }
}
