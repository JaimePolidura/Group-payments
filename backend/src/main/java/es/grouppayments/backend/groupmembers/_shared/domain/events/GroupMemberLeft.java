package es.grouppayments.backend.groupmembers._shared.domain.events;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class GroupMemberLeft extends DomainEvent {
    private final UUID userId;
    private final UUID roomId;
}
