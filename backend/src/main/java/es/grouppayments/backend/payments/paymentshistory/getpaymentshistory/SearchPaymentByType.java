package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;

import java.util.function.Predicate;

public enum SearchPaymentByType {
    GROUP_PAYMENT(payment -> payment.getType() == PaymentType.GROUP_PAYMENT),
    TRANSFERENCE(payment -> payment.getType() == PaymentType.TRANSFERENCE),
    ALL(payment -> true);

    public final Predicate<Payment> paymentTypeMatcherPredicate;

    SearchPaymentByType(Predicate<Payment> matcher) {
        this.paymentTypeMatcherPredicate = matcher;
    }
}
