package es.grouppayments.backend.payments.payments._shared.domain.events.transfer;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class ErrorWhileMakingTransfer extends DomainEvent implements OnlineNotificableClientEvent, LogeableEvent {
    @Getter private final UUID from;
    @Getter private final String fromUsername;
    @Getter private final UUID to;
    @Getter private final double money;
    @Getter private final Currency currencyCode;
    @Getter private final String description;
    @Getter private final String errorCause;

    @Override
    public List<UUID> to() {
        return List.of(from);
    }

    @Override
    public String name() {
        return "transfer-error";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "from", this.from,
                "errorCause", this.errorCause
        );
    }
}
