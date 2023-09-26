package es.grouppayments.backend.eventlog._shared.infrastructure;

import es.grouppayments.backend.eventlog._shared.domain.EventLog;
import es.grouppayments.backend.eventlog._shared.domain.EventLogRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryEventLogRepository implements EventLogRepository {
    private final Map<UUID, EventLog> events;

    public InMemoryEventLogRepository(){
        this.events = new ConcurrentHashMap<>();
    }

    @Override
    public void save(EventLog event) {
        this.events.put(event.getEventLogId(), event);
    }

    @Override
    public Optional<EventLog> findById(UUID eventLogId) {
        return Optional.ofNullable(this.events.get(eventLogId));
    }

    @Override
    public List<EventLog> findByEventName(String eventName) {
        return this.events.values()
                .stream()
                .filter(eventLog -> eventLog.getEventName().equalsIgnoreCase(eventName))
                .toList();
    }

    @Override
    public List<EventLog> findByUserId(UUID userId) {
        return this.events.values()
                .stream()
                .filter(eventLog -> eventLog.getUsersId().contains(userId))
                .toList();
    }
}
