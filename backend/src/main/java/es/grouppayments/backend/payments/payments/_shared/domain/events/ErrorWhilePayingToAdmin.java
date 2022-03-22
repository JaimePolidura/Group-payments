package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhilePayingToAdmin extends GroupDomainEvent {
    @Getter private final Group group;
    @Getter private final String reason;
    @Getter private final UUID member;
    @Getter private final double money;

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
                "reason", this.reason
        );
    }
}
