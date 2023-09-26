package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountcreated;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import org.junit.Test;

import java.util.UUID;

public final class OnStripeConnectedAccountCreatedTest extends OnStripeConnectedAccountCreatedTestMother{
    @Test
    public void shouldSave(){
        UUID userId = UUID.randomUUID();
        addPaymentInfo(userId);
        final String connectedAccountId = "newconnecetacoksalkja";

        on(new StripeConnectedAccountCreated(userId, connectedAccountId));

        assertUserPaymentInfoContent(userId, paymentUserInfo -> paymentUserInfo.getConnectedAccountId().equals(connectedAccountId));
    }
}
