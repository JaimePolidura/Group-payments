package es.grouppayments.backend.paymenttransaction.onpaymentdone;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.payments.PaymentDone;
import es.grouppayments.backend.paymenttransaction._shared.domain.PaymentTransactionsService;

public class OnPaymentDoneMother extends TestMother {
    private final OnPaymentDone onPaymentDone;

    public OnPaymentDoneMother(){
        this.onPaymentDone = new OnPaymentDone(
                new PaymentTransactionsService(super.paymentTransactionRepository)
        );
    }

    public void execute(PaymentDone paymentDone){
        this.onPaymentDone.on(paymentDone);
    }
}
