package _shared.eventbus;

import es.jaime.javaddd.domain.event.DomainEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FakeTestEventBus implements TestEventBus {
    private final Map<Class<? extends DomainEvent>, DomainEvent> eventsRaised;

    public FakeTestEventBus() {
        this.eventsRaised = new HashMap<>();
    }

    @Override
    public void publish(List<DomainEvent> list) {
        list.forEach(event -> this.eventsRaised.put(event.getClass(), event));
    }

    @Override
    public void publish(DomainEvent domainEvent) {
        this.eventsRaised.put(domainEvent.getClass(), domainEvent);
    }

    @Override
    public Set<Class<? extends DomainEvent>> getAllEventRaised(){
        return this.eventsRaised.keySet();
    }

    @Override
    public int getNumberOfEventRaised(){
        return this.eventsRaised.size();
    }

    @Override
    public DomainEvent getEvent(Class<? extends DomainEvent> eventClass){
        return this.eventsRaised.get(eventClass);
    }

    @Override
    public boolean isRaised(Class<? extends DomainEvent> eventClass){
        return this.eventsRaised.containsKey(eventClass);
    }
}
