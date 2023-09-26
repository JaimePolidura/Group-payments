package es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment;

import es.grouppayments.backend._shared.domain.events.GroupEvent;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class GroupPaymentInitialized extends DomainEvent implements GroupEvent, OnlineNotificableClientEvent, LogeableEvent {
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
