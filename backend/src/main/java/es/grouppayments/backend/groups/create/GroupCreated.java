package es.grouppayments.backend.groups.create;

import es.grouppayments.backend._shared.domain.events.GroupEvent;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupCreated extends DomainEvent implements GroupEvent, LogeableEvent {
    @Getter private final UUID groupId;
    @Getter private final UUID adminUserId;

    @Override
    public String name() {
        return "group-created";
    }

    @Override
    public List<UUID> to() {
        return List.of(this.adminUserId);
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId,
                "adminUserId", adminUserId
        );
    }
}
