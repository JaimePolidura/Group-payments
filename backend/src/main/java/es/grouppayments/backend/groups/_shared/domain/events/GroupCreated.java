package es.grouppayments.backend.groups._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupCreated extends DomainEvent implements GroupDomainEvent {
    @Getter private final UUID groupId;
    @Getter private final UUID adminUserId;

    @Override
    public String name() {
        return "group-created";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId,
                "adminUserId", adminUserId
        );
    }
}
