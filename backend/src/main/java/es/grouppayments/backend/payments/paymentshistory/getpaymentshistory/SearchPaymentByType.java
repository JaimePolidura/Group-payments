package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;

import java.util.function.Predicate;

public enum SearchPaymentByType {
    GROUP_PAYMENT(payment -> payment.getContext() == PaymentContext.GROUP_PAYMENT),
    TRANSFERENCE(payment -> payment.getContext() == PaymentContext.TRANSFERENCE),
    ALL(payment -> true);

    public final Predicate<Payment> paymentTypeMatcherPredicate;

    SearchPaymentByType(Predicate<Payment> matcher) {
        this.paymentTypeMatcherPredicate = matcher;
    }
}
