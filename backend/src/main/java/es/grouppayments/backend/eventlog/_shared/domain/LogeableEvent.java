package es.grouppayments.backend.eventlog._shared.domain;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * All events that will be stored in eventlog will have to implement this interface
 */
public interface LogeableEvent extends AsyncDomainEvent {
    Map<String, Object> body();
    String name();
    List<UUID> to();
}
