package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer;

import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.TransferDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnTransferDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({TransferDone.class})
    public void on(TransferDone event){
        this.paymentHistoryService.save(event.getFrom(), event.getTo(), event.getCurrencyCode(), event.getMoney(),
                event.getDescription(), PaymentContext.TRANSFERENCE);
    }
}
