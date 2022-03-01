package _shared;

import es.jaime.javaddd.domain.event.DomainEvent;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public interface UsingTestEventBus {
    TestEventBus testEventBus();

    default void assertEventRaised(Class<? extends DomainEvent>... events){
        Arrays.stream(events).forEach(event -> assertTrue(testEventBus().isRaised(event)));
    }

    default <T extends DomainEvent> void assertContentOfEventEquals(Class<T> event, Function<T, Object> dataAccessor, Object toEq){
        assertEquals(dataAccessor.apply((T) testEventBus().getEvent(event)), toEq);
    }
}
