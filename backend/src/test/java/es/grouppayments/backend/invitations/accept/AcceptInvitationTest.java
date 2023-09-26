package es.grouppayments.backend.invitations.accept;

import es.grouppayments.backend.groupmembers.join.JoinGroupCommand;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public final class AcceptInvitationTest extends AcceptInvitationTestMother{
    @Test
    public void shouldDispatchJoinGroupCommand(){
        UUID invitationId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID fromUserId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();
        addInvitation(invitationId, groupId, fromUserId, toUserId);

        execute(new AcceptInvitationCommand(invitationId, toUserId));

        assertJoinGroupCommandDisptched();
        assertInvitationDeleted(invitationId);
        assertContentOfJoinGroupCommand( command -> command.getGroupId().equals(groupId));
        assertContentOfJoinGroupCommand( command -> command.getUserId().equals(toUserId));
    }

    @Test(expected = ResourceNotFound.class)
    public void invitationNotExists(){
        execute(new AcceptInvitationCommand(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test(expected = NotTheOwner.class)
    public void userNotBelongsToInvitation(){
        UUID invitationId = UUID.randomUUID();
        addInvitation(invitationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        execute(new AcceptInvitationCommand(invitationId, UUID.randomUUID()));
    }
}

