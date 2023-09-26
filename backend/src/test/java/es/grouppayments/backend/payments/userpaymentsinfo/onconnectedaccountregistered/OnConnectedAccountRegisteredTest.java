package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountregistered;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import org.junit.Test;

import java.util.UUID;

public final class OnConnectedAccountRegisteredTest extends OnConnectedAccountRegisteredTestMother{
    @Test
    public void shouldSave(){
        UUID userId = UUID.randomUUID();
        addPaymentInfo(userId, false);

        super.on(new StripeConnectedAccountRegistered(userId));

        assertUserPaymentInfoContent(userId, PaymentUserInfo::isAddedDataInStripeConnectedAccount);
    }
}
