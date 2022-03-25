package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.onapppayingadmindone;

import es.grouppayments.backend.payments.payments._shared.domain.events.AppPayingAdminDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnAppPayingAdminDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({AppPayingAdminDone.class})
    public void on (AppPayingAdminDone event){
        this.paymentHistoryService.save(event.getGroup().getAdminUserId(), event.getMoney(),
                event.getGroup().getDescription(), PaymentType.APP_TO_ADMIN);
    }
}
