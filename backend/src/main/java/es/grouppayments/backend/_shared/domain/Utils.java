package es.grouppayments.backend._shared.domain;

import es.jaime.javaddd.domain.exceptions.DomainException;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Utils {
    private Utils () {}

    public static <E> void allMatchOrThrow(List<E> list, Predicate<E> condition,
                                           Supplier<? extends DomainException> exceptionSupplier) {
        if(!list.stream().allMatch(condition))
           throw exceptionSupplier.get();
    }
}
