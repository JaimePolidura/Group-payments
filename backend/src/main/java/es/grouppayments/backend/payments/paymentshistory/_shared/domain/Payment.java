package es.grouppayments.backend.payments.paymentshistory._shared.domain;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@ToString
public class Payment extends Aggregate implements Comparable<Payment> {
    @Getter private final UUID paymentId;
    @Getter private final UUID fromUserId;
    @Getter private final UUID toUserId;
    @Getter private final double money;
    @Getter private final Currency currency;
    @Getter private final LocalDateTime date;
    @Getter private final String description;
    @Getter private final PaymentState state;
    @Getter private final PaymentContext context;
    @Getter private final String errorMessage;

    @Override
    public int compareTo(Payment o) {
        return o.getDate().compareTo(this.date);
    }
}
