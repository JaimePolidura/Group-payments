package es.grouppayments.backend.groups._shared.domain.events;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.Data;

import java.util.UUID;

@Data
public class GroupCreated extends DomainEvent {
    private final UUID groupId;
}
