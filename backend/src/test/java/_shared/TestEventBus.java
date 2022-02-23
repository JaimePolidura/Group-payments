package _shared;

import es.jaime.javaddd.domain.event.DomainEvent;
import es.jaime.javaddd.domain.event.EventBus;

import java.util.Set;

public interface TestEventBus extends EventBus {
    Set<Class<? extends DomainEvent>> getAllEventRaised();

    DomainEvent getEvent(Class<? extends DomainEvent> eventClass);

    int getNumberOfEventRaised();

    boolean isRaised(Class<? extends DomainEvent> eventClass);
}
