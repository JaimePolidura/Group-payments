package es.grouppayments.backend.invitations.onuserdeleted;

import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import org.junit.Test;

import java.util.UUID;

public final class OnUserDeletedInvitationsTest extends OnUserDeletedInvitationsTestMother{
    @Test
    public void shouldDelete(){
        UUID userId = UUID.randomUUID();
        UUID inviationId1 = UUID.randomUUID();
        UUID inviationId2 = UUID.randomUUID();
        addInvitation(inviationId1, UUID.randomUUID(), userId, UUID.randomUUID());
        addInvitation(inviationId2, UUID.randomUUID(), UUID.randomUUID(), userId);

        on(new UserDeleted(userId));

        assertInvitationDeleted(inviationId1);
        assertInvitationDeleted(inviationId2);
    }

    @Test
    public void shouldntDelete(){
        on(new UserDeleted(UUID.randomUUID()));
    }
}
