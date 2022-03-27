package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;

import java.util.function.Predicate;

public enum SearchPaymentByType {
    MEMBER_TO_APP(payment -> payment.getType() == PaymentType.USER_TO_APP),
    APP_TO_ADMIN(payment -> payment.getType() == PaymentType.APP_TO_USER),
    ALL(payment -> true);

    public final Predicate<Payment> paymentTypeMatcherPredicate;

    SearchPaymentByType(Predicate<Payment> matcher) {
        this.paymentTypeMatcherPredicate = matcher;
    }
}
