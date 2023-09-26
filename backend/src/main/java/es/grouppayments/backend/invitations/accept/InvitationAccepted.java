package es.grouppayments.backend.invitations.accept;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class InvitationAccepted extends DomainEvent implements LogeableEvent {
    @Getter private final UUID groupId;
    @Getter private final UUID fromUserId;
    @Getter private final UUID toUserId;

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId,
                "userId", this.toUserId,
                "fromUserId", this.fromUserId
        );
    }

    @Override
    public String name() {
        return "group-invitations-accepted";
    }

    @Override
    public List<UUID> to() {
        return List.of(toUserId, fromUserId);
    }
}
