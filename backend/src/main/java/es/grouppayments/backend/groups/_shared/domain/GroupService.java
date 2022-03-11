package es.grouppayments.backend.groups._shared.domain;

import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.groups._shared.domain.events.GroupEdited;
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

    public void create(UUID groupId, String title, double money, UUID adminUserId){
        groups.save(new Group(groupId, title, LocalDateTime.now(), money, adminUserId));

        this.eventBus.publish(new GroupCreated(groupId, adminUserId));
    }

    public void update(Group groupEdited){
        this.groups.save(groupEdited);

        this.eventBus.publish(new GroupEdited(groupEdited));
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

    public void deleteGroupIfIsAdmin(UUID userId){
        Optional<Group> groupAdminOptional = findByUsernameHost(userId);
        boolean isAdminOfGroup = groupAdminOptional.isPresent();

        if(isAdminOfGroup){
            deleteById(groupAdminOptional.get().getGroupId());
        }
    }
}
