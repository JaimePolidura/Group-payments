package es.grouppayments.backend.users.users.delete.confirm;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class ConfirmDeleteAccountCommand implements Command {
    @Getter private final String token;
}
