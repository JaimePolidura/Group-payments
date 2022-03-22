package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class StripeConnectedAccountCreated extends DomainEvent {
    @Getter private final UUID userId;
    @Getter private final String connectedAccountId;
}
