package es.grouppayments.backend.payments.onpaymentdone;

import es.grouppayments.backend.payments.PaymentTestMother;
import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.PaymentHistoricalService;

public class OnPaymentDoneMother extends PaymentTestMother {
    private final OnPaymentDone onPaymentDone;

    public OnPaymentDoneMother(){
        this.onPaymentDone = new OnPaymentDone(
                new PaymentHistoricalService(paymentRepository())
        );
    }

    public void execute(PaymentDone paymentDone){
        this.onPaymentDone.on(paymentDone);
    }
}
