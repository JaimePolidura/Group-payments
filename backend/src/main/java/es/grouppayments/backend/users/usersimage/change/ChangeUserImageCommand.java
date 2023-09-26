package es.grouppayments.backend.users.usersimage.change;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class ChangeUserImageCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final byte[] imageContent;
}
