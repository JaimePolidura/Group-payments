package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhileMemberPaying extends GroupDomainEvent {
    private final UUID groupId;
    @Getter private final String reason;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-payment-error-member-paying";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.groupId,
                "reason", this.reason
        );
    }
}
