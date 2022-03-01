package es.grouppayments.backend.groupmembers.join;

import es.grouppayments.backend.groupmembers._shared.domain.events.GroupMemberLeft;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import org.junit.Test;

import java.util.UUID;

public class JoinGroupTest extends JoinGroupTestMother{
    @Test
    public void shouldJoinMember(){
        UUID groupId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        executeJoinGroupCommandHandler(groupId, userId);

        assertMemberInGroup(groupId, userId);
        assertEventRaised(GroupMemberJoined.class);
    }

    @Test
    public void shouldJoinGroupWhenMemberOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToJoin = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(groupIdToLeave, UUID.randomUUID(), 10, userId);
        addGroup(groupIdToJoin, UUID.randomUUID());

        executeJoinGroupCommandHandler(groupIdToJoin, userId);

        assertMemberInGroup(groupIdToJoin, userId);
        assertEventRaised(GroupMemberJoined.class, GroupMemberLeft.class);
    }

    @Test
    public void shouldJoinGroupWhenAdminOfOtherGroup(){
        UUID groupIdToLeave = UUID.randomUUID();
        UUID groupIdToJoin = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        addGroup(groupIdToLeave, userId);
        addGroup(groupIdToJoin, UUID.randomUUID());

        executeJoinGroupCommandHandler(groupIdToJoin, userId);

        assertGroupCreated(groupIdToJoin);
        assertEventRaised(GroupMemberJoined.class, GroupDeleted.class);
    }
}
