package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class PaymentInitialized extends GroupDomainEvent {
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