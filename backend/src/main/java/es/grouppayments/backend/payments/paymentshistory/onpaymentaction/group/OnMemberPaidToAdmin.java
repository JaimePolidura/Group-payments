package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group;

import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.MemberPaidToAdmin;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnMemberPaidToAdmin {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({MemberPaidToAdmin.class})
    public void on(MemberPaidToAdmin event){
        this.paymentHistoryService.save(event.getMemberUserId(), event.getGroup().getAdminUserId(), event.getCurrencyCode(),
                event.getMoney(), event.getGroup().getDescription(), PaymentType.GROUP_PAYMENT);
    }
}
