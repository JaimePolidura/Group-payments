package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class GroupPaymentInitialized extends DomainEvent implements GroupDomainEvent {
    private final UUID groupId;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-payment-initialized";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.groupId.toString()
        );
    }
}
