package es.grouppayments.backend._shared.domain;

import es.jaime.javaddd.domain.async.AsyncDomainEvent;

import java.util.UUID;

public interface GroupDomainEvent extends AsyncDomainEvent {
    UUID getGroupId();
}
