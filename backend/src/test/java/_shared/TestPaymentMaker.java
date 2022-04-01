package _shared;

import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;

public interface TestPaymentMaker extends PaymentMakerService {
    double getAllMoneyPaid();
    int getNumebrOfTimesMembersPaid();
    void willFail();
}
