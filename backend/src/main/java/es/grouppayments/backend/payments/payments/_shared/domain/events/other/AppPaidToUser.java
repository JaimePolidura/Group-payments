package es.grouppayments.backend.payments.payments._shared.domain.events.other;

import es.grouppayments.backend._shared.domain.SuccessfulPaymentDomainEvent;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.PaymentType;
import es.jaime.javaddd.domain.event.DomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class AppPaidToUser extends DomainEvent implements SuccessfulPaymentDomainEvent {
    private final double money;
    private final String currencyCode;
    private final String description;
    private final UUID userId;

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "money", this.money,
                "currencyCode", this.currencyCode,
                "description", this.description,
                "userId", userId,
                "state", state()
        );
    }

    @Override
    public String name() {
        return "payments-sucessful-app-user";
    }

    @Override
    public double money() {
        return this.money;
    }

    @Override
    public String currencyCode() {
        return this.currencyCode;
    }

    @Override
    public PaymentType paymentType() {
        return PaymentType.APP_TO_USER;
    }

    @Override
    public String description() {
        return this.description;
    }

    @Override
    public UUID userId() {
        return this.userId;
    }
}
