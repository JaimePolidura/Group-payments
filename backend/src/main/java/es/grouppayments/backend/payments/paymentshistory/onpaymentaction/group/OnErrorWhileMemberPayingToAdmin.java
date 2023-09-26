package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group;

import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.ErrorWhilePayingToGroupAdmin;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhileMemberPayingToAdmin {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhilePayingToGroupAdmin.class})
    public void on(ErrorWhilePayingToGroupAdmin event){
        this.paymentHistoryService.save(event.getUserId(), event.getGroup().getAdminUserId(), event.getCurrencyCode(),
                event.getMoney(), event.getGroup().getDescription(), PaymentContext.GROUP_PAYMENT, event.getErrorMessage());
    }
}
