package es.grouppayments.backend.groups._shared.domain;

import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.groups._shared.domain.events.GroupEdited;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
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
        groups.save(new Group(groupId, title, LocalDateTime.now(), money, adminUserId, GroupState.PROCESS));

        this.eventBus.publish(new GroupCreated(groupId, adminUserId));
    }

    public void changeState(UUID gruopId, GroupState state){
        Group group = this.findByIdOrThrowException(gruopId)
                .changeStateTo(state);
        this.groups.deleteById(gruopId);
        this.groups.save(group);
    }

    public void update(Group groupEdited){
        this.ensureGroupEditableOrThrow(groupEdited);

        this.groups.deleteById(groupEdited.getGroupId());
        this.groups.save(groupEdited);

        this.eventBus.publish(new GroupEdited(groupEdited));
    }

    private void ensureGroupEditableOrThrow(Group group){
        if(!group.isEditable())
            throw new IllegalState("Group editing is blocked");
    }

    public Group findByIdOrThrowException(UUID groupId){
        return this.groups.findById(groupId)
                .orElseThrow(() -> new ResourceNotFound("Group for that id doesnt exists"));
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
