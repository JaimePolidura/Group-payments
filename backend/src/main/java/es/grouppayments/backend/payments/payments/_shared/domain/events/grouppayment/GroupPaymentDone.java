package es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment;

import es.grouppayments.backend._shared.domain.events.GroupEvent;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupPaymentDone extends DomainEvent implements GroupEvent, OnlineNotificableClientEvent, LogeableEvent {
    @Getter private final UUID groupId;
    @Getter private final List<UUID> membersUsersId;
    @Getter private final UUID adminUserId;
    @Getter private final String description;
    @Getter private final double moneyPaidPerMember;

    @Override
    public String name() {
        return "group-payment-all-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "groupId", groupId,
                "membersUsersId", membersUsersId,
                "adminUserId", adminUserId,
                "description", description,
                "moneyPaidPerMember", moneyPaidPerMember
        );
    }

    @Override
    public List<UUID> to() {
        return new ArrayList<>();
    }
}
