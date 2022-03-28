package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.payments.payments._shared.domain.events.other.ErrorUserPaidToApp;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhileGroupMemberPaying extends DomainEvent implements GroupDomainEvent, NotificableClientDomainEvent {
    @Getter private final Group group;
    @Getter private final double money;
    @Getter private final String currencyCode;
    @Getter private final String description;
    @Getter private final UUID userId;
    @Getter private final String errorMessage;

    @Override
    public UUID getGroupId() {
        return this.group.getGroupId();
    }

    @Override
    public String name() {
        return "group-payment-error-member-paying";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.group.getGroupId(),
                "errorMessage", errorMessage,
                "groupMemberUserId", this.getUserId()
        );
    }

    @Override
    public List<UUID> to() {
        return new ArrayList<>();
    }
}
