package es.grouppayments.backend.payments.userpaymentsinfo.oncustomercreated;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;
import org.junit.Test;

import java.util.UUID;

public final class OnCustomerCreatedTest extends OnCustomerCreatedTestMother{
    @Test
    public void shouldSave(){
        UUID userId = UUID.randomUUID();
        on(new StripeCustomerCreated(userId, DEFAULT_CUSTOMER_ID, DEFAULT_PAYMENT_METHOD));

        assertUserPaymentInfoCreated(userId);
        assertUserPaymentInfoContent(userId, paymentUserInfo -> paymentUserInfo.getCustomerId().equals(DEFAULT_CUSTOMER_ID));
        assertUserPaymentInfoContent(userId, paymentUserInfo -> paymentUserInfo.getPaymentMethod().equals(DEFAULT_PAYMENT_METHOD));
        assertUserPaymentInfoContent(userId, paymentUserInfo -> !paymentUserInfo.isAddedDataInStripeConnectedAccount());
        assertUserPaymentInfoContent(userId, paymentUserInfo -> paymentUserInfo.getConnectedAccountId() == null);
    }
}
