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
    @Getter private final LocalDateTime date;
    @Getter private final String payer;
    @Getter private final String paid;
    @Getter private final double money;
    @Getter private final String description;
    @Getter private final PaymentState state;
    @Getter private final PaymentType type;
    @Getter private final String errorMessage;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "paymentId", this.paymentId,
                "date", this.date.toString(),
                "payer", this.payer,
                "paid", this.paid,
                "money", this.money,
                "description", this.description,
                "state", this.state.toString(),
                "type", this.type.toString(),
                "errorMessage", this.errorMessage
        );
    }

    @Override
    public int compareTo(Payment o) {
        return o.getDate().compareTo(this.date);
    }
}
