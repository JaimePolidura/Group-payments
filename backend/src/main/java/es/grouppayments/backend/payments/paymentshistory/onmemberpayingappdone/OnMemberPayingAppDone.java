package es.grouppayments.backend.payments.paymentshistory.onmemberpayingappdone;

import es.grouppayments.backend.payments.payments._shared.domain.events.MemberPayingAppDone;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnMemberPayingAppDone {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({MemberPayingAppDone.class})
    public void on(MemberPayingAppDone event){
        this.paymentHistoryService.save(event.getGroupMemberUserId(), event.getMoney(),
                event.getGroup().getDescription(), PaymentType.MEMBER_TO_APP);
    }
}
