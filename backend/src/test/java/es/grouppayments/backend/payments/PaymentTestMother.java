package es.grouppayments.backend.payments;

import _shared.TestMother;
import _shared.UsingPayments;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;
import es.grouppayments.backend.payments.paymentshistory._shared.infrastructure.PaymentRepositoryInMemory;

public class PaymentTestMother extends TestMother implements UsingPayments {
    private final PaymentsHistoryRepository paymentRepository;

    public PaymentTestMother(){
        this.paymentRepository = new PaymentRepositoryInMemory();
    }

    @Override
    public PaymentsHistoryRepository paymentRepository() {
        return this.paymentRepository;
    }
}
