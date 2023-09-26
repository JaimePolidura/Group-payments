package es.grouppayments.backend.invitations.reject;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class InvitationRejected extends DomainEvent implements OnlineNotificableClientEvent, LogeableEvent {
    private final UUID invitationId;
    private final UUID toUserId;
    private final UUID fromUserId;
    private final UUID groupId;

    @Override
    public List<UUID> to() {
        return List.of(fromUserId);
    }

    @Override
    public String name() {
        return "group-invitations-rejected";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "invitationId", this.invitationId,
                "toUserId", this.toUserId,
                "fromUserId", this.fromUserId,
                "groupId", this.groupId
        );
    }
}
