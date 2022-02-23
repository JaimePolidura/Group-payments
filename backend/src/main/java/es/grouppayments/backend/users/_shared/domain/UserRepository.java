package es.grouppayments.backend.users._shared.domain;

public interface UserRepository {
    void save(User user);

    boolean existsByUsername(String username);
}
