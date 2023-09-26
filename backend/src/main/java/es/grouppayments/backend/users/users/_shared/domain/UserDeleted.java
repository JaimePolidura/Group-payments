package es.grouppayments.backend.users.users._shared.domain;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class UserDeleted extends DomainEvent implements OnlineNotificableClientEvent, LogeableEvent {
    @Getter private final UUID userId;
    
    @Override
    public Map<String, Object> body() {
        return Map.of("userId", this.userId.toString());
    }

    @Override
    public String name() {
        return "user-deleted";
    }

    @Override
    public List<UUID> to() {
        return List.of(userId);
    }
}
