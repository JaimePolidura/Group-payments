package es.grouppayments.backend.users.users.delete.prepare;


import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class PrepareDeleteAccountCommand implements Command {
    @Getter private final UUID userId;
}
