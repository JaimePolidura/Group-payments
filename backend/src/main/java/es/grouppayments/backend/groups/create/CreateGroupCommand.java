package es.grouppayments.backend.groups.create;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class CreateGroupCommand implements Command {
    @Getter private final UUID groupId;
    @Getter private final UUID userId;
    @Getter private final double money;
    @Getter private final String title;
}
