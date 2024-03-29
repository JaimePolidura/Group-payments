package es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment;

import es.grouppayments.backend._shared.domain.events.GroupEvent;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhilePayingToGroupAdmin extends DomainEvent implements GroupEvent, OnlineNotificableClientEvent, LogeableEvent {
    @Getter private final double money;
    @Getter private final Currency currencyCode;
    @Getter private final String description;
    @Getter private final UUID userId;
    @Getter private final String errorMessage;
    @Getter private final Group group;

    @Override
    public UUID getGroupId() {
        return this.getGroup().getGroupId();
    }

    @Override
    public String name() {
        return "group-payment-error-paying-admin";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.getGroup().getGroupId(),
                "errorMessage", this.errorMessage,
                "userId", this.userId
        );
    }

    @Override
    public List<UUID> to() {
        return new ArrayList<>();
    }
}
