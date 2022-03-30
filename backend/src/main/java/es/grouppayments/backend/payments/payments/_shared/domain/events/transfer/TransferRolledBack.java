package es.grouppayments.backend.payments.payments._shared.domain.events.transfer;

import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend._shared.domain.events.SuccessfulPaymentDomainEvent;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class TransferRolledBack extends DomainEvent implements SuccessfulPaymentDomainEvent, NotificableClientDomainEvent {
    private final UUID userId;
    private final double money;
    private final String currencyCode;
    private final String description;
    private final String reasonOfRollingback;

    @Override
    public List<UUID> to() {
        return null;
    }

    @Override
    public double getMoney() {
        return this.money;
    }

    @Override
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    @Override
    public PaymentType getPaymentType() {
        return PaymentType.APP_TO_USER;
    }

    @Override
    public String getDescription() {
        return String.format("Successfully rolledback transfer error: %s", this.description);
    }

    @Override
    public UUID getUserId() {
        return this.userId;
    }

    @Override
    public String name() {
        return "transference-rolledback";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "money", this.money,
                "currencyCode", this.currencyCode,
                "userId", userId,
                "reasonOfRollingback", this.reasonOfRollingback
        );
    }
}
