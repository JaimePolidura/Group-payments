package es.grouppayments.backend.payments;

import es.jaime.javaddd.domain.exceptions.DomainException;

public class UnprocessablePayment extends DomainException {
    public UnprocessablePayment(String message) {
        super(message);
    }
}
