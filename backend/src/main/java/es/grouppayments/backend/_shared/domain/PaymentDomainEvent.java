package es.grouppayments.backend._shared.domain;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentState;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.UUID;

public interface PaymentDomainEvent extends AsyncDomainEvent {
    double getMoney();
    String getCurrencyCode();
    PaymentType getPaymentType();
    String getDescription();
    UUID getUserId();
    PaymentState getState();
}