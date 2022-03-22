package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class AppPayingAdminDone extends GroupDomainEvent {
    @Getter private final double money;
    @Getter private final Group group;

    @Override
    public UUID getGroupId() {
        return this.group.getGroupId();
    }

    @Override
    public String name() {
        return "group-payment-app-admin-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.group.getAdminUserId(),
                "adminUserId", this.group.getAdminUserId(),
                "money", money
        );
    }
}
