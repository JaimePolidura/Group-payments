package es.grouppayments.backend._shared.domain;

import es.grouppayments.backend._shared.domain.messages.AynchEvent;
import es.jaime.javaddd.domain.event.DomainEvent;

import java.util.UUID;

public abstract class GroupDomainEvent extends DomainEvent implements AynchEvent {
    public abstract UUID getGroupId();
}
