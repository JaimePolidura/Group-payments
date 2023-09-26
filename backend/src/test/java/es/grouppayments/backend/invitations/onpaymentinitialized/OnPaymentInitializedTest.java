package es.grouppayments.backend.invitations.onpaymentinitialized;

import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupPaymentInitialized;
import org.junit.Test;

import java.util.UUID;

public final class OnPaymentInitializedTest extends OnPaymentInitializedTestMother{
    @Test
    public void shouldDelete(){
        UUID invitationId1 = UUID.randomUUID();
        UUID invitationId2 = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();
        addInvitation(invitationId1, groupId, UUID.randomUUID(), toUserId);
        addInvitation(invitationId2, groupId, UUID.randomUUID(), toUserId);

        on(new GroupPaymentInitialized(groupId));

        assertInvitationDeleted(invitationId1);
        assertInvitationDeleted(invitationId2);
    }
}
