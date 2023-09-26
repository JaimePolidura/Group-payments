package es.grouppayments.backend.payments.userpaymentsinfo._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@ToString
public final class PaymentUserInfo extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final String paymentMethod;
    @Getter private final String customerId;
    @Getter private final String connectedAccountId;
    @Getter private final boolean addedDataInStripeConnectedAccount;

    public static PaymentUserInfo create(UUID userId, String customerId, String paymentMethod){
        return new PaymentUserInfo(userId, paymentMethod, customerId, null, false);
    }

    public PaymentUserInfo setPaymentMethod(String newPaymentMethod){
        return new PaymentUserInfo(userId, newPaymentMethod, customerId, connectedAccountId, false);
    }

    public PaymentUserInfo setConnectedAccountId(String newConnectedAccountId){
        return new PaymentUserInfo(userId, paymentMethod, customerId, newConnectedAccountId, false);
    }

    public PaymentUserInfo setAddedDataInStripeConnectedAccount(){
        return new PaymentUserInfo(userId, paymentMethod, customerId, connectedAccountId, true);
    }
}
