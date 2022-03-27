package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.payments.payments._shared.domain.events.other.UserPaidToApp;

import java.util.Map;
import java.util.UUID;

public final class GroupMemberPayingAppDone extends UserPaidToApp implements GroupDomainEvent {
    private final Group group;

    public GroupMemberPayingAppDone(double money, String currencyCode, String description, UUID userId, Group group) {
        super(money, currencyCode, description, userId);
        this.group = group;
    }

    @Override
    public UUID getGroupId() {
        return this.group.getGroupId();
    }

    @Override
    public String name() {
        return "group-payment-member-app-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", this.group.getGroupId(),
                "groupMemberUserId", getUserId(),
                "money", getMoney()
        );
    }
}
