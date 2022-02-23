package es.grouppayments.backend.groups._shared.domain;

import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepository groups;
    private final EventBus eventBus;

    public void create(String title, double money, UUID adminUserId){
        UUID gruopId = UUID.randomUUID();

        groups.save(new Group(gruopId, title, LocalDateTime.now(), money, GroupStatus.CREATED, adminUserId));

        this.eventBus.publish(new GroupCreated(gruopId));
    }

    public Optional<Group> findById(UUID groupId){
        return this.groups.findById(groupId);
    }

    public Optional<Group> findByUsernameHost(UUID userId){
        return this.groups.findByUsernameHost(userId);
    }

    public void deleteById(UUID gropId){
        this.groups.deleteById(gropId);

        this.eventBus.publish(new GroupDeleted(gropId));
    }
}
