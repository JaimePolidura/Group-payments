package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.onapppayingadmindone;

import es.grouppayments.backend.payments.payments._shared.domain.events.AppPayingGroupAdminDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnAppPayingAdminDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({AppPayingGroupAdminDone.class})
    public void on (AppPayingGroupAdminDone event){
        this.paymentHistoryService.save(event.getGroup().getAdminUserId(), event.getMoney(),
                event.getGroup().getDescription(), PaymentType.APP_TO_USER);
    }
}
