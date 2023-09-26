package es.grouppayments.backend.invitations.reject;

import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public final class RejectInvitationTest extends RejectInvitationTestMother{
    @Test
    public void shouldReject() {
        UUID invitationId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();

        addInvitation(invitationId, UUID.randomUUID(), UUID.randomUUID(), toUserId);
        execute(new RejectInvitationCommand(invitationId, toUserId));

        assertInvitationDeleted(invitationId);
        assertEventRaised(InvitationRejected.class);
    }

    @Test(expected = ResourceNotFound.class)
    public void invitationNotFound(){
        execute(new RejectInvitationCommand(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test(expected = NotTheOwner.class)
    public void incorrectInvitation(){
        UUID invitationId = UUID.randomUUID();
        addInvitation(invitationId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        execute(new RejectInvitationCommand(invitationId, UUID.randomUUID()));
    }
}
