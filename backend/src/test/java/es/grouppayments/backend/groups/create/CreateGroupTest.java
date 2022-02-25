package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers._shared.domain.events.GroupMemberLeft;
import es.grouppayments.backend.groupmembers.join.GroupMemberJoined;
import es.grouppayments.backend.groups._shared.domain.events.GroupCreated;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import org.junit.Test;

import java.util.UUID;


public class CreateGroupTest extends CreateGroupTestMother {
    @Test
    public void shouldCreateGroup(){
        UUID groupId = UUID.randomUUID();
        executeCreateGroupCommandHandler(groupId);

        assertGroupCreated(groupId);
        assertEventRaised(GroupCreated.class);
    }

    @Test
    public void shouldCreateGroupWhenMemberOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToCreate = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(groupIdToLeave, UUID.randomUUID());
        addMember(groupIdToLeave, userId);

        executeCreateGroupCommandHandler(groupIdToCreate, userId);

        assertGroupCreated(groupIdToCreate);
        assertEventRaised(GroupCreated.class, GroupMemberLeft.class);
    }

    @Test
    public void shouldCreateGroupWhenAdminOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToCreate = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(groupIdToLeave, userId);

        executeCreateGroupCommandHandler(groupIdToCreate, userId);

        assertGroupCreated(groupIdToCreate);
        assertEventRaised(GroupCreated.class, GroupDeleted.class);
    }
}
