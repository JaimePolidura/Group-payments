package es.grouppayments.backend.notifications._shared.domain;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.List;
import java.util.UUID;

public interface NotificableEvent extends AsyncDomainEvent {
    List<UUID> to();
}
