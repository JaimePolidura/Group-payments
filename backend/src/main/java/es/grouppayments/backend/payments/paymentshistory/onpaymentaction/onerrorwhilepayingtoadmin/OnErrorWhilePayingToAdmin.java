package es.grouppayments.backend.payments.paymentshistory.onpaymentaction.onerrorwhilepayingtoadmin;

import es.grouppayments.backend.payments.payments._shared.domain.events.ErrorWhilePayingToAdmin;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentHistoryService;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnErrorWhilePayingToAdmin {
    private final PaymentHistoryService paymentHistoryService;

    @EventListener({ErrorWhilePayingToAdmin.class})
    public void on(ErrorWhilePayingToAdmin event){
        this.paymentHistoryService.save(event.getMember(), event.getMoney(), event.getGroup().getDescription(),
                PaymentType.APP_TO_ADMIN, event.getReason());
    }
}
