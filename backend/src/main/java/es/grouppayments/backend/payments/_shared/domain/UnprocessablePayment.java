package es.grouppayments.backend.payments._shared.domain;

import es.jaime.javaddd.domain.exceptions.DomainException;

public class UnprocessablePayment extends DomainException {
    public UnprocessablePayment(String message) {
        super(message);
    }
}
