package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class StripeConnectedAccountCreated extends DomainEvent implements LogeableEvent {
    @Getter private final UUID userId;
    @Getter private final String connectedAccountId;

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "userId", this.userId,
                "connectedAccountId", this.connectedAccountId
        );
    }

    @Override
    public String name() {
        return "payments-userpaymentsinfo-stripeconnectedaccountcreated";
    }

    @Override
    public List<UUID> to() {
        return List.of(userId);
    }
}

