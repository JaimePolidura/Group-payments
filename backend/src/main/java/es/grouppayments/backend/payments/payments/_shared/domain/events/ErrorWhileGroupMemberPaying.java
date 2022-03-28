package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.payments.payments._shared.domain.events.other.ErrorUserPaidToApp;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public final class ErrorWhileGroupMemberPaying extends ErrorUserPaidToApp implements GroupDomainEvent {
    @Getter private final Group group;

    public ErrorWhileGroupMemberPaying(double money, String currencyCode, String description, UUID userId, String errorMessage, Group group) {
        super(money, currencyCode, description, userId, errorMessage);
        this.group = group;
    }

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
                "errorMessage", this.errorMessage(),
                "groupMemberUserId", this.getUserId()
        );
    }
}
