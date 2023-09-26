package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class StripeCustomerCreated extends DomainEvent implements LogeableEvent {
    @Getter private final UUID userId;
    @Getter private final String customerId;
    @Getter private final String paymentMethodId;

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "userId", this.userId,
                "customerId", this.customerId,
                "paymentMethodId", this.paymentMethodId
        );
    }

    @Override
    public String name() {
        return "payments-userpaymentsinfo-stripecustomercreated";
    }

    @Override
    public List<UUID> to() {
        return List.of(this.userId);
    }
}
