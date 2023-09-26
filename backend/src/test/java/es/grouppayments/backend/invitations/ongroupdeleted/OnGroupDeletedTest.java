package es.grouppayments.backend.invitations.ongroupdeleted;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import org.junit.Test;

import java.util.UUID;

public final class OnGroupDeletedTest extends OnGroupDeletedTestMother{
    @Test
    public void shouldDelete(){
        UUID invitationId1 = UUID.randomUUID();
        UUID invitationId2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();
        addInvitation(invitationId1, groupId, UUID.randomUUID(), toUserId);
        addInvitation(invitationId2, groupId, UUID.randomUUID(), toUserId);

        on(new GroupDeleted(groupId));

        assertInvitationDeleted(invitationId1);
        assertInvitationDeleted(invitationId2);
    }
}
