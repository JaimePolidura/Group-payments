package es.grouppayments.backend.groups._shared.infrastructure;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public final class GroupsRepositoryInMemory implements GroupRepository {
    private final Set<Group> groups;

    public GroupsRepositoryInMemory(){
        this.groups = new HashSet<>();
    }

    @Override
    public void save(Group group) {
        this.save(group);
    }

    @Override
    public Optional<Group> findById(UUID groupId) {
        return this.groups.stream()
                .filter(group -> group.getGroupId().equals(group))
                .findFirst();
    }

    @Override
    public Optional<Group> findByUsernameHost(UUID userId) {
        return this.groups.stream()
                .filter(group -> group.getGroupId().equals(group))
                .findFirst();
    }

    @Override
    public void deleteById(UUID groupId) {
        this.groups.removeIf(group -> group.getGroupId().equals(groupId));
    }
}
