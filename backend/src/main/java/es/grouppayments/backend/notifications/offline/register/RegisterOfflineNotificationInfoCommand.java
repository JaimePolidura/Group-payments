package es.grouppayments.backend.notifications.offline.register;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class RegisterOfflineNotificationInfoCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final String token;
}
