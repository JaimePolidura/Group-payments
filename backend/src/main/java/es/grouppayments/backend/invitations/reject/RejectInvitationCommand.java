package es.grouppayments.backend.invitations.reject;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class RejectInvitationCommand implements Command {
    @Getter private final UUID invitationId;
    @Getter private final UUID userId;
}
