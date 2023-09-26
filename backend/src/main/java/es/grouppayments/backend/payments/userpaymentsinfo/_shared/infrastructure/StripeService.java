package es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure;

import com.stripe.model.Account;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;

import java.util.Date;
import java.util.UUID;

public interface StripeService {
    String setupIntent();
    void createCustomer(UUID userId, String paymentMethodId);
    Account createConnectedAccount(UUID userId, Dob dob);
    boolean hasRegisteredInConnectedAccount(UUID userId);
    String createConnectedAccountLink(String connectedAccountId);
}
