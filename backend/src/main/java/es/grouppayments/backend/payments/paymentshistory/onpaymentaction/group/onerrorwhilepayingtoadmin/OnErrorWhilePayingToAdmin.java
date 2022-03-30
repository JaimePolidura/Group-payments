package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.group.onerrorwhilepayingtoadmin;

import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.ErrorWhilePayingToGroupAdmin;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhilePayingToAdmin {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhilePayingToGroupAdmin.class})
    public void on(ErrorWhilePayingToGroupAdmin event){
        this.paymentHistoryService.save(event.getUserId(), event.getMoney(), event.getGroup().getDescription(),
                PaymentType.APP_TO_USER, event.getErrorMessage());
    }
}
