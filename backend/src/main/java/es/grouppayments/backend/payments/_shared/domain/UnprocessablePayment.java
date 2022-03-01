package es.grouppayments.backend.payments._shared.domain;

import es.jaime.javaddd.domain.exceptions.DomainException;

import java.util.function.Supplier;

public class UnprocessablePayment extends DomainException {
    public UnprocessablePayment(String message) {
        super(message);
    }

    public static Supplier<UnprocessablePayment> of(String message){
        return () -> new UnprocessablePayment(message);
    }
}
