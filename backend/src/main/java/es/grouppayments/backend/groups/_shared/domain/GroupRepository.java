package es.grouppayments.backend.groups._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface GroupRepository {
    void save(Group group);

    Optional<Group> findById(UUID groupId);

    Optional<Group> findByUsernameHost(UUID userId);

    void deleteById(UUID groupId);
}
