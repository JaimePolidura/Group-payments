package es.grouppayments.backend.groupmembers.leave;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.Data;

import java.util.UUID;

@Data
public class LeaveGroupCommand implements Command {
    private final UUID groupId;
    private final UUID userId;
}
