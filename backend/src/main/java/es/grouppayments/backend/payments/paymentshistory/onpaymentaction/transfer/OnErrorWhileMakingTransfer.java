package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer;

import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.ErrorWhileMakingTransfer;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhileMakingTransfer {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhileMakingTransfer.class})
    public void on(ErrorWhileMakingTransfer event){
        this.paymentHistoryService.save(event.getFrom(), event.getTo(), event.getCurrencyCode(), event.getMoney(),
                event.getDescription(), PaymentContext.TRANSFERENCE, event.getErrorCause());
    }
}
