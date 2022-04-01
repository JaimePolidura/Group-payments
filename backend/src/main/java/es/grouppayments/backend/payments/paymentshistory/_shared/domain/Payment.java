package es.grouppayments.backend.payments.paymentshistory._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@ToString
public class Payment extends Aggregate implements Comparable<Payment> {
    @Getter private final UUID paymentId;
    @Getter private final UUID fromUserId;
    @Getter private final UUID toUserId;
    @Getter private final double money;
    @Getter private final String currency;
    @Getter private final LocalDateTime date;
    @Getter private final String description;
    @Getter private final PaymentState state;
    @Getter private final PaymentType type;
    @Getter private final String errorMessage;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "paymentId", this.paymentId,
                "fromUserId", this.fromUserId,
                "toUserId", this.toUserId,
                "money", this.money,
                "currency", this.currency,
                "date", this.date,
                "description", this.description,
                "state", this.state,
                "type", this.type,
                "errorMessage", this.errorMessage
        );
    }

    @Override
    public int compareTo(Payment o) {
        return o.getDate().compareTo(this.date);
    }
}
