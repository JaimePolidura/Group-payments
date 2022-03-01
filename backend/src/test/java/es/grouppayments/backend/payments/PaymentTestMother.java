package es.grouppayments.backend.payments;

import _shared.TestMother;
import _shared.UsingPayments;
import es.grouppayments.backend.payments._shared.domain.PaymentRepository;
import es.grouppayments.backend.payments._shared.infrastructure.PaymentRepositoryInMemory;

public class PaymentTestMother extends TestMother implements UsingPayments {
    private final PaymentRepository paymentRepository;

    public PaymentTestMother(){
        this.paymentRepository = new PaymentRepositoryInMemory();
    }

    @Override
    public PaymentRepository paymentRepository() {
        return this.paymentRepository;
    }
}
