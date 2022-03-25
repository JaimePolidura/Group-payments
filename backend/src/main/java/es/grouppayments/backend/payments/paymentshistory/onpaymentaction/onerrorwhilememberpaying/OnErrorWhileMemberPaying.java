package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.onerrorwhilememberpaying;

import es.grouppayments.backend.payments.payments._shared.domain.events.ErrorWhileMemberPaying;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhileMemberPaying {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhileMemberPaying.class})
    public void on(ErrorWhileMemberPaying event){
        paymentHistoryService.save(event.getGroupMemberUserId(), event.getGroup().getMoney(), event.getGroup().getDescription(),
                PaymentType.MEMBER_TO_APP, event.getReason());
    }
}
