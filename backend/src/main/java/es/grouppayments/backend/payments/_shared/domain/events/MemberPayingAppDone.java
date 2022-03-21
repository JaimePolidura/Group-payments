package es.grouppayments.backend.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class MemberPayingAppDone extends GroupDomainEvent {
    private final UUID groupId;
    private final UUID groupMemberUserId;
    private final double quantity;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-payment-member-app-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId.toString(),
                "groupMemberUserId", groupMemberUserId.toString(),
                "quantity", quantity
        );
    }
}
