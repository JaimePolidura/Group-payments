package es.grouppayments.backend._shared.domain.events;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.UUID;

public interface GroupEvent extends AsyncDomainEvent {
    UUID getGroupId();
}
