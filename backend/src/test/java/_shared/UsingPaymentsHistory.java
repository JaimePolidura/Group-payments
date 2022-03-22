package _shared;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;

import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsingPaymentsHistory {
    PaymentsHistoryRepository paymentsHistoryRepository();

    default void assertPaymentHistorySaved(UUID userId){
        var found = this.paymentsHistoryRepository().findByUserIdPaid(userId).size() > 0 ||
                this.paymentsHistoryRepository().findByUserIdPayer(userId).size() > 0;

        assertTrue(found);
    }

    default void assertContentOfPayment(UUID userId, Predicate<Payment> content){
        assertPaymentHistorySaved(userId);

        Payment payment = this.paymentsHistoryRepository().findByUserIdPaid(userId).isEmpty() ?
                this.paymentsHistoryRepository().findByUserIdPayer(userId).get(0) :
                this.paymentsHistoryRepository().findByUserIdPaid(userId).get(0);

        assertTrue(content.test(payment));
    }
}
