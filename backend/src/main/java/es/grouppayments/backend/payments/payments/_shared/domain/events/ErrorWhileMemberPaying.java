package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhileMemberPaying extends GroupDomainEvent {
    @Getter private final Group group;
    @Getter private final String reason;
    @Getter private final UUID groupMemberUserId;

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
                "reason", this.reason,
                "groupMemberUserId", this.groupMemberUserId
        );
    }
}
