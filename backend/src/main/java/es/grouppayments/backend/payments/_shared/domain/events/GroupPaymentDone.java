package es.grouppayments.backend.payments._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupPaymentDone extends GroupDomainEvent {
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
}
