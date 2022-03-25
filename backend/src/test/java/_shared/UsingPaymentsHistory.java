package _shared;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;

import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsingPaymentsHistory {
    PaymentsHistoryRepository paymentsHistoryRepository();

    default void assertPaymentHistorySaved(UUID userId){
        assertTrue(this.paymentsHistoryRepository().findByUserId(userId).size() > 0);
    }

    default void assertContentOfPayment(UUID userId, Predicate<Payment> content){
        assertPaymentHistorySaved(userId);

        Payment payment = this.paymentsHistoryRepository().findByUserId(userId)
                        .get(0);

        assertTrue(content.test(payment));
    }
}
