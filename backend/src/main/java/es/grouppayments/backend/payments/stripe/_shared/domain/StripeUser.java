package es.grouppayments.backend.payments.stripe._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@ToString
public final class StripeUser extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final String paymentMethod;
    @Getter private final String customerId;
    @Getter private final String connectedAccountId;

    public static StripeUser create(UUID userId, String customerId){
        return new StripeUser(userId, null, customerId, null);
    }

    public StripeUser setPaymentMethod(String newPaymentMethod){
        return new StripeUser(userId, newPaymentMethod, customerId, connectedAccountId);
    }

    public StripeUser setConnectedAccountId(String newConnectedAccountId){
        return new StripeUser(userId, paymentMethod, customerId, newConnectedAccountId);
    }

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "userId", userId,
               "paymentMethod", paymentMethod,
                "customerId", customerId,
                "connectedAccountId", connectedAccountId
        );
    }

}
