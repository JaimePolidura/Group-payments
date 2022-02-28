package es.grouppayments.backend.paymenttransaction.onpaymentdone;

import es.grouppayments.backend.payments.PaymentDone;
import es.grouppayments.backend.paymenttransaction._shared.domain.PaymentTransactionsService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OnPaymentDone {
    private final PaymentTransactionsService paymentTransactions;
    
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
