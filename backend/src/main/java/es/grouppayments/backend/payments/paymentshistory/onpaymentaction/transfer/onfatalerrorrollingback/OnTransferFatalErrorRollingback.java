package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.transfer.onfatalerrorrollingback;

import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.TransferFatalErrorRollingback;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnTransferFatalErrorRollingback {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({TransferFatalErrorRollingback.class})
    public void on(TransferFatalErrorRollingback event){
        this.paymentHistoryService.save(event.getUserId(), event.getMoney(), event.getDescription(),
                PaymentType.APP_TO_USER, event.errorMessage());
    }
}
