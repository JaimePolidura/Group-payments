package es.grouppayments.backend._shared.domain.events;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.UUID;

public interface GroupDomainEvent extends AsyncDomainEvent {
    UUID getGroupId();
}
