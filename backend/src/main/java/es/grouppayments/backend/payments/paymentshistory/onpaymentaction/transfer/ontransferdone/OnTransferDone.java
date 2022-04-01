package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.ontransferdone;

import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.porro.TransferDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnTransferDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({TransferDone.class})
    public void on(TransferDone event){
        //FROM
        this.paymentHistoryService.save(event.getFrom(), event.getMoneyUserFromPaid(), event.getDescription(), PaymentType.USER_TO_APP);

        //TO
        this.paymentHistoryService.save(event.getTo(), event.getMoneyUserToGotPaid(), event.getDescription(), PaymentType.APP_TO_USER);
    }
}
