package es.grouppayments.backend.groups._shared.domain.events;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class GroupDeleted extends DomainEvent {
    @Getter private final UUID groupId;
}
