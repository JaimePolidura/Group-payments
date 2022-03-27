package _shared;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentsHistoryRepository;
import es.jaime.javaddd.domain.Utils;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsingPaymentsHistory {
    PaymentsHistoryRepository paymentsHistoryRepository();

    default void addRandoms(UUID userId, int number){
        Utils.repeat(number, () -> addRandom(userId));
    }

    default void addRandom(UUID userId){
        PaymentType paymentType = Math.random() < 0.5 ? PaymentType.APP_TO_USER : PaymentType.USER_TO_APP;
        boolean isUserIdAdmin = Math.random() < 0.5;
        boolean isError = Math.random() < 0.3;

        this.paymentsHistoryRepository().save(new Payment(
                UUID.randomUUID(),
                LocalDateTime.now(),
                paymentType == PaymentType.APP_TO_USER ? "APP" : userId.toString(),
                paymentType == PaymentType.USER_TO_APP ? "APP" : userId.toString(),
                Math.random() * 100,
                "Payment",
                isError ? PaymentState.ERROR : PaymentState.SUCCESS,
                paymentType,
                isError ? "Not enough balance" : null
        ));
    }

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
