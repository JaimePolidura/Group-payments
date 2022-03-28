package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.payments.payments._shared.domain.events.other.AppPaidToUser;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public final class AppPayingGroupAdminDone extends AppPaidToUser implements GroupDomainEvent {
    @Getter private final Group group;

    public AppPayingGroupAdminDone(double money, String currencyCode, String description, UUID userId, Group group) {
        super(money, currencyCode, description, userId);
        this.group = group;
    }

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
                "money", this.getMoney()
        );
    }
}
