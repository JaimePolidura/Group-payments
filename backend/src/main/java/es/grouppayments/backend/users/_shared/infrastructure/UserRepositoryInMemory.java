package es.grouppayments.backend.users._shared.infrastructure;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public final class UserRepositoryInMemory implements UserRepository {
    private final Set<User> users;

    public UserRepositoryInMemory() {
        this.users = new HashSet<>();
    }

    @Override
    public void save(User user) {
        this.users.add(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.users.stream()
                .anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }
}
