package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onmemberpayingappdone;

import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupMemberPayingAppDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnMemberPayingAppDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({GroupMemberPayingAppDone.class})
    public void on(GroupMemberPayingAppDone event){
        this.paymentHistoryService.save(event.getUserId(), event.getMoney(),
                event.getDescription(), PaymentType.USER_TO_APP);
    }
}
