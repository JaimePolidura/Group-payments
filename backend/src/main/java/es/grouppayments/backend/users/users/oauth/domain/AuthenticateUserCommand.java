package es.grouppayments.backend.users.users.oauth.domain;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class AuthenticateUserCommand implements Command {
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final String photoUrl;
    @Getter private final String countryCode;
}
