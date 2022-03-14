package es.grouppayments.backend.groupmembers.kick;

import es.grouppayments.backend.groupmembers._shared.domain.events.GroupMemberLeft;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public class KickGroupMemberTest extends KickGroupMemberTestMother{
    @Test
    public void shouldKickGroupMember(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID userIdToKick = UUID.randomUUID();
        addGroup(groupId, userId, 100, userIdToKick);

        execute(userId, groupId, userIdToKick);

        assertMemberDeleted(userIdToKick);
        assertEventRaised(GroupMemberLeft.class);
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntKickGroupNotExists(){
        execute(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntKickNotTheAdmin(){
        UUID groupId = UUID.randomUUID();
        UUID userIdAdmin = UUID.randomUUID();
        UUID userIdKicker = UUID.randomUUID();
        addGroup(groupId, userIdAdmin);

        execute(userIdKicker, groupId, UUID.randomUUID());
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldKickUserNotInGroup(){
        UUID groupId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID userIdToKick = UUID.randomUUID();
        addGroup(groupId, userId);

        execute(userId, groupId, userIdToKick);
    }

    @Test(expected = IllegalState.class)
    public void shouldntKickHimself(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId);

        execute(userId, groupId, userId);
    }

    @Test(expected = IllegalState.class)
    public void shouldntKickInvalidState(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID userIdToKick = UUID.randomUUID();
        addGroup(groupId, userId, 100, userIdToKick);
        changeStateTo(groupId, GroupState.PAYING);

        execute(userId, groupId, userIdToKick);
    }

}
