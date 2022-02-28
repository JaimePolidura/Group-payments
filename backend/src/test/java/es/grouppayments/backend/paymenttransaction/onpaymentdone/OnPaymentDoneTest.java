package es.grouppayments.backend.paymenttransaction.onpaymentdone;

import es.grouppayments.backend.payments.PaymentDone;
import es.grouppayments.backend.paymenttransaction._shared.domain.PaymentTransaction;
import org.junit.Test;

import java.util.List;
import java.util.UUID;


public class OnPaymentDoneTest extends OnPaymentDoneMother {
    @Test
    public void shouldSaveTransaction(){
        UUID user1 = UUID.randomUUID();
        UUID user2 = UUID.randomUUID();
        UUID userAdmin = UUID.randomUUID();

        execute(new PaymentDone(List.of(user1, user2), userAdmin, "payment", 10));

        //It will get all transactions
        List<PaymentTransaction> transactions = paymentTransactionRepository.findByUserIdPaid(userAdmin);

        assertCollectionSize(transactions, 2);
        assertContentListMatches(transactions, transaction -> transaction.getDescription().equalsIgnoreCase("payment"));
        assertContentListMatches(transactions, transaction -> transaction.getMoney() == 10);

        assertEmptyCollection(paymentTransactionRepository.findByUserIdPaid(user1));
    }
}
