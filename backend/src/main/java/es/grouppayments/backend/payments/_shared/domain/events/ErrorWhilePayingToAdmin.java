package es.grouppayments.backend.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhilePayingToAdmin extends GroupDomainEvent {
    private final UUID groupId;
    private final String reason;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-payment-error-paying-admin";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.groupId,
                "reason", this.reason
        );
    }
}
