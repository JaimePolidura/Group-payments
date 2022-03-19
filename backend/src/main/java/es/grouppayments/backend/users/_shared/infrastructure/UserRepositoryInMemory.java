package es.grouppayments.backend.users._shared.infrastructure;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public final class UserRepositoryInMemory implements UserRepository {
    private final Set<User> users;

    public UserRepositoryInMemory() {
        this.users = new HashSet<>();
    }

    @Override
    public void save(User user) {
        this.users.remove(user);
        this.users.add(user);
    }

    @Override
    public Optional<User> findByUserId(UUID id) {
        return this.users.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
