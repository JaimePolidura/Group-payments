package es.grouppayments.backend.users.users.edit;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class EditUserCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final String username;
    @Getter private final Currency currency;
    @Getter private final String countryCode;
}
