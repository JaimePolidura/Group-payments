package es.grouppayments.backend.payments.paymenteshistory.onpaymentaction;

import _shared.TestMother;
import _shared.UsingPaymentsHistory;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;
import es.grouppayments.backend.payments.paymentshistory._shared.infrastructure.PaymentRepositoryInMemory;

public class PaymentHistoryTestMother extends TestMother implements UsingPaymentsHistory {
    private final PaymentsHistoryRepository paymentsHistoryRepository;

    public PaymentHistoryTestMother(){
        this.paymentsHistoryRepository = new PaymentRepositoryInMemory();
    }

    @Override
    public PaymentsHistoryRepository paymentsHistoryRepository() {
        return this.paymentsHistoryRepository;
    }
}
