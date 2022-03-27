package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.onerrorwhilememberpaying;

import es.grouppayments.backend.payments.payments._shared.domain.events.ErrorWhileGroupMemberPaying;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhileMemberPaying {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhileGroupMemberPaying.class})
    public void on(ErrorWhileGroupMemberPaying event){
        paymentHistoryService.save(event.getUserId(), event.getGroup().getMoney(), event.getGroup().getDescription(),
                PaymentType.USER_TO_APP, event.getReason());
    }
}
