package es.grouppayments.backend.payments._shared.infrastructure;

import es.grouppayments.backend.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments._shared.domain.UnprocessablePayment;
import es.grouppayments.backend.payments.stripe._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FakePaymentMakerService implements PaymentMakerService {
    private final StripeService stripeService;

    @Override
    public void paymentMemberToApp(UUID userId, double money) {

    }

    @Override
    public void paymentAppToAdmin(UUID userId, double money) throws Exception {

    }
}
