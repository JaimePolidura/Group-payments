package es.grouppayments.backend.payments.onpaymentdone;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.PaymentHistoricalService;

public class OnPaymentDoneMother extends TestMother {
    private final OnPaymentDone onPaymentDone;

    public OnPaymentDoneMother(){
        this.onPaymentDone = new OnPaymentDone(
                new PaymentHistoricalService(super.paymentTransactionRepository)
        );
    }

    public void execute(PaymentDone paymentDone){
        this.onPaymentDone.on(paymentDone);
    }
}
