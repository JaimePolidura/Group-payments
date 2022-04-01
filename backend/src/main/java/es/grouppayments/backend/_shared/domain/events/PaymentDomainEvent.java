package es.grouppayments.backend._shared.domain.events;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentContext;
import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.UUID;

public interface PaymentDomainEvent extends AsyncDomainEvent {
    double getMoney();
    String getCurrencyCode();
    PaymentContext getPaymentType();
    String getDescription();
    UUID getUserId();
    PaymentState getState();
}
