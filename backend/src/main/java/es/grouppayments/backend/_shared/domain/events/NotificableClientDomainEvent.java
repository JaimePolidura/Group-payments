package es.grouppayments.backend._shared.domain.events;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.List;
import java.util.UUID;

/**
 * All events that can be notifcitaed to the client will
 * implement this interface
 */
public interface NotificableClientDomainEvent extends AsyncDomainEvent {
    List<UUID> to();
}
