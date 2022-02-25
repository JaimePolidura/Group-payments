package es.grouppayments.backend.payments;

import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class FakePaymentService implements PaymentService{
    private final EventBus eventBus;

    @Override
    public void makePayment(UUID memberUserId, double moneyPerMember) {

    }
}
