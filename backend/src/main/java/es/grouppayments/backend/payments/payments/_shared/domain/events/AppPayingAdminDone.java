package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class AppPayingAdminDone extends GroupDomainEvent {
    private final UUID groupId;
    private final UUID adminUserId;
    private final double money;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-payment-app-admin-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId.toString(),
                "adminUserId", adminUserId.toString(),
                "money", money
        );
    }
}
