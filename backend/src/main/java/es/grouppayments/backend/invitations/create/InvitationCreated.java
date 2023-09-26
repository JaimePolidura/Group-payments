package es.grouppayments.backend.invitations.create;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OfflineNotificableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class InvitationCreated extends DomainEvent implements OnlineNotificableClientEvent,
        OfflineNotificableEvent, LogeableEvent {

    private final UUID invitationId;
    private final UUID toUserId;
    private final UUID fromUserId;
    private final UUID groupId;
    private final String description;
    private final LocalDateTime date;

    @Override
    public List<UUID> to() {
        return List.of(this.toUserId);
    }

    @Override
    public String name() {
        return "group-invitations-created";
    }

    @Override
    public String title() {
        return "Invitation recieved";
    }

    @Override
    public String message() {
        return String.format("You recived an invitation to join group %s", this.description);
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "toUserId", this.toUserId,
                "fromUserId", this.fromUserId,
                "groupId", this.groupId,
                "invitationId", this.invitationId,
                "description", this.description,
                "date", this.date.toString()
        );
    }
}
