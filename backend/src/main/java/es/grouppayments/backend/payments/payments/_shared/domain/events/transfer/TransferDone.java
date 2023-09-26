package es.grouppayments.backend.payments.payments._shared.domain.events.transfer;

import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import es.grouppayments.backend.notifications._shared.domain.OfflineNotificableEvent;
import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class TransferDone extends DomainEvent implements OnlineNotificableClientEvent, OfflineNotificableEvent, LogeableEvent {
    @Getter private final UUID from;
    @Getter private final String fromUsername;
    @Getter private final UUID to;
    @Getter private final double money;
    @Getter private final Currency currencyCode;
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
    public String title() {
        return "Transfer recieved";
    }

    @Override
    public String message() {
        return String.format("You recieved %s%s from %s", this.money, this.currencyCode.symbol, this.fromUsername);
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
