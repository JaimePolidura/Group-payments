package es.grouppayments.backend.groupmembers.kick;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.Data;

import java.util.UUID;

@Data
public class KickGroupMemberCommand implements Command {
    private final UUID userId;
    private final UUID groupId;
    private final UUID userIdToKick;
}
