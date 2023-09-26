package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers.leave.GroupMemberLeft;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.invitations.create.InvitationCreated;
import org.junit.Test;

import java.util.List;
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
    public void shouldCreateGroupWithInvitations(){
        UUID groupId = UUID.randomUUID();
        UUID userIdCreator = UUID.randomUUID();
        UUID user1ToInvite = UUID.randomUUID();
        UUID user2ToInvite = UUID.randomUUID();
        UUID user3ToInvite = UUID.randomUUID(); //Not exists
        addUser(userIdCreator);
        addUser(user1ToInvite, "user1ToInvite@gmail.com");
        addUser(user2ToInvite, "user2ToInvite@gmail.com");

        executeCreateGroupCommandHandler(groupId, userIdCreator, List.of("user1ToInvite@gmail.com",
                "user2ToInvite@gmail.com", "user3ToInvite@gmail.com"));

        assertGroupCreated(groupId);
        assertEventRaised(GroupCreated.class);

        assertEventRaised(InvitationCreated.class);
        assertInvitationCreated(user1ToInvite, groupId);
        assertInvitationCreated(user2ToInvite, groupId);
        assertInvitationNotCreated(user3ToInvite, groupId);
    }

    @Test
    public void shouldCreateGroupWhenMemberOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToCreate = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID userAdmin = UUID.randomUUID();

        addUser(userId, userAdmin);

        addGroup(groupIdToLeave, userAdmin, 1, userId);

        executeCreateGroupCommandHandler(groupIdToCreate, userId);

        assertGroupCreated(groupIdToCreate);
        assertEventRaised(GroupCreated.class, GroupMemberLeft.class);
    }

    @Test
    public void shouldCreateGroupWhenAdminOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToCreate = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addUser(userId);

        addGroup(groupIdToLeave, userId);

        executeCreateGroupCommandHandler(groupIdToCreate, userId);

        assertGroupCreated(groupIdToCreate);
        assertEventRaised(GroupCreated.class, GroupDeleted.class);
    }
}
