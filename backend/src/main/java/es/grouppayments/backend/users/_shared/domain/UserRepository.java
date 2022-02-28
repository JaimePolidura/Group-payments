package es.grouppayments.backend.users._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UUID id);

    boolean existsByUsername(String username);
}
