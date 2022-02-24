package es.grouppayments.backend.groupmembers.join;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class GroupMemberJoined extends DomainEvent {
    @Getter private final UUID userId;
    @Getter private final UUID groupId;
}
