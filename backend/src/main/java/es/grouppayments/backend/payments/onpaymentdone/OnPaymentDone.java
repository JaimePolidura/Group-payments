package es.grouppayments.backend.payments.onpaymentdone;

import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.PaymentHistoricalService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnPaymentDone {
    private final PaymentHistoricalService paymentTransactions;
    
    @EventListener({PaymentDone.class})
    public void on(PaymentDone event){
        event.getMembersUsersId().forEach(memberUserId -> {
            paymentTransactions.save(
                    event.getAdminUserId(),
                    memberUserId,
                    event.getMoneyPaidPerUser(),
                    event.getDescription()
            );
        });
    }
}
