package es.grouppayments.backend.payments.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.payments.payments._shared.domain.events.other.ErrorAppPaidToUser;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

public final class ErrorWhilePayingToGroupAdmin extends ErrorAppPaidToUser implements GroupDomainEvent {
    @Getter private final Group group;

    public ErrorWhilePayingToGroupAdmin(double money, String currencyCode, String description, UUID userId, String errorMessage, Group group) {
        super(money, currencyCode, description, userId, errorMessage);
        this.group = group;
    }

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
                "errorMessage", this.errorMessage()
        );
    }
}
