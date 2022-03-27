package es.grouppayments.backend._shared.domain;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;

public interface SuccessfulPaymentDomainEvent extends PaymentDomainEvent{
    @Override
    default PaymentState getState() {
        return PaymentState.SUCCESS;
    }
}
