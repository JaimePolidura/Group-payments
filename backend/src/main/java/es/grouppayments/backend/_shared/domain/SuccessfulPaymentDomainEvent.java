package es.grouppayments.backend._shared.domain;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import org.springframework.security.core.parameters.P;

public interface SuccessfulPaymentDomainEvent extends PaymentDomainEvent{
    @Override
    default PaymentState state() {
        return PaymentState.SUCCESS;
    }
}
