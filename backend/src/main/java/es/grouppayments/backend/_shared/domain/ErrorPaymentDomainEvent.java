package es.grouppayments.backend._shared.domain;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;

public interface ErrorPaymentDomainEvent extends PaymentDomainEvent{
    String errorMessage();

    @Override
    default PaymentState getState() {
        return PaymentState.ERROR;
    }
}
