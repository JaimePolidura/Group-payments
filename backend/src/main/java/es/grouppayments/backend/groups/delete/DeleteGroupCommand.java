package es.grouppayments.backend.groups.delete;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class DeleteGroupCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final UUID groupId;
}
