package es.grouppayments.backend.groupmembers.join;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class JoinGroupCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final UUID groupId;
}
