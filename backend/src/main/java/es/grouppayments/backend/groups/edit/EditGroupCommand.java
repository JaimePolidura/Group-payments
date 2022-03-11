package es.grouppayments.backend.groups.edit;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class EditGroupCommand implements Command {
    @Getter private final UUID groupId;
    @Getter private final UUID userId;
    @Getter private final double newMoney;
    @Getter private final String newDescription;
}
