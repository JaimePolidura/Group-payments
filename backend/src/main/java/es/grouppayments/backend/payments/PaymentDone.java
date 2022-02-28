package es.grouppayments.backend.payments;

import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PaymentDone extends DomainEvent {
    @Getter private final List<UUID> membersUsersId;
    @Getter private final UUID adminUserId;
    @Getter private final String description;
    @Getter private final double moneyPaidPerUser;
}
