package es.grouppayments.backend.payments.payments._shared.domain.events.transfer.porro;

import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class TransferDone extends DomainEvent implements NotificableClientDomainEvent {
    @Getter private final UUID from;
    @Getter private final String fromUsername;
    @Getter private final UUID to;
    @Getter private final double money;
    @Getter private final String currencyCode;
    @Getter private final String description;

    @Override
    public List<UUID> to() {
        return List.of(this.to, this.from);
    }

    @Override
    public String name() {
        return "transfer-done";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
            "from", this.from,
            "to", this.to,
            "fromUsername", this.fromUsername,
            "money", this.money,
            "currencyCode", this.currencyCode,
            "description", this.description
        );
    }
}
