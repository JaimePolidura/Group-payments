package es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class GroupPaymentInitialized extends DomainEvent implements GroupDomainEvent, NotificableClientDomainEvent {
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

    @Override
    public List<UUID> to() {
        return new ArrayList<>();
    }
}
