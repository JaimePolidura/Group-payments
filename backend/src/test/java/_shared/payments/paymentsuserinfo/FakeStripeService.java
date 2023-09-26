package _shared.payments.paymentsuserinfo;

import com.stripe.model.Account;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;

import java.util.UUID;

public final class FakeStripeService implements StripeService {
    protected final String INTENT_DEFAULT = "intentxd";
    protected final String CONNECTED_ACCOUNT_LINK = "link";

    @Override
    public String setupIntent() {
        return this.INTENT_DEFAULT;
    }

    @Override
    public void createCustomer(UUID userId, String paymentMethodId) {
    }

    @Override
    public Account createConnectedAccount(UUID userId, Dob date) {
        return null;
    }

    @Override
    public boolean hasRegisteredInConnectedAccount(UUID userId) {
        return false;
    }

    @Override
    public String createConnectedAccountLink(String connectedAccountId) {
        return this.CONNECTED_ACCOUNT_LINK;
    }
}
