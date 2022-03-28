package es.grouppayments.backend._shared.domain;

import es.jaime.javaddd.domain.exceptions.DomainException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Utils {
    private Utils () {}

    public static <E> void allMatchOrThrow(List<E> list, Predicate<E> condition,
                                           Supplier<? extends DomainException> exceptionSupplier) {
        if(!list.stream().allMatch(condition))
           throw exceptionSupplier.get();
    }

    public static <E> Set<E> setOf(E... elements){
        return new HashSet<>(Arrays.asList(elements));
    }

    public static double deductFrom(double total, double fee){
        return total * ((100 - fee) / 100);
    }

    public static <T, O> List<O> map(List<T> initialList, Function<T, O> mapper){
        return initialList.stream()
                .map(mapper)
                .toList();
    }

    public static boolean isSucess(Runnable runnable){
        try{
            runnable.run();

            return true;
        }catch (Throwable e){
            return false;
        }
    }
}
