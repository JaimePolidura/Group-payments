package es.grouppayments.backend.invitations.create;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.Getter;

import java.util.UUID;

public final class CreateInvitationCommand implements Command {
    @Getter private final UUID groupId;
    @Getter private final UUID toUserId;
    @Getter private final UUID fromUserId;
    @Getter private final UUID invitationId;

    public CreateInvitationCommand(UUID groupId, UUID toUserId, UUID fromUserId) {
        this.groupId = groupId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.invitationId = UUID.randomUUID();
    }

    public CreateInvitationCommand(UUID groupId, UUID toUserId, UUID fromUserId,
                                   UUID invitationId) {
        this.groupId = groupId;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.invitationId = invitationId;
    }
}
