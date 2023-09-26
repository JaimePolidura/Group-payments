package es.grouppayments.backend.users.users._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);

    Optional<User> findByUserId(UUID id);

    Optional<User> findByEmail(String email);

    void deleteByUserId(UUID userId);
}
