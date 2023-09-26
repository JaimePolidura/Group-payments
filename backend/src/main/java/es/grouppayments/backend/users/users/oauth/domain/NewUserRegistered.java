package es.grouppayments.backend.users.users.oauth.domain;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class NewUserRegistered extends DomainEvent {
    @Getter private final User user;
}
