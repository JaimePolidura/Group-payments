package _shared;

import es.grouppayments.backend.payments._shared.domain.PaymentRepository;

public interface UsingPayments {
    PaymentRepository paymentRepository();
}
