package es.grouppayments.backend.payments.payments._shared.domain.events.transfer;

import es.grouppayments.backend._shared.domain.events.ErrorPaymentDomainEvent;
import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class TransferErrorAppPaidToUser extends DomainEvent implements ErrorPaymentDomainEvent, NotificableClientDomainEvent {
    private final double money;
    private final String currencyCode;
    private final String description;
    private final UUID userId;
    private final String errorMessage;

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "money", this.money,
                "currencyCode", this.currencyCode,
                "description", this.description,
                "userId", userId,
                "errorMessage", errorMessage,
                "state", getState()
        );
    }

    @Override
    public String name() {
        return "transfer-payments-error-app-user";
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
        return this.description;
    }

    @Override
    public UUID getUserId() {
        return this.userId;
    }

    @Override
    public String errorMessage() {
        return this.errorMessage;
    }

    @Override
    public List<UUID> to() {
        return List.of(userId);
    }
}
