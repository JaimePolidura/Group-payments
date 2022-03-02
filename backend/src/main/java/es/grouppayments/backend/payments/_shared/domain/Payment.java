package es.grouppayments.backend.payments._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Payment extends Aggregate {
    @Getter private final UUID paymentId;
    @Getter private final UUID userIdPaid;
    @Getter private final UUID userIdPayer;
    @Getter private final double money;
    @Getter private final LocalDateTime payDate;
    @Getter private final String description;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "paymentId", paymentId.toString(),
                "userIdPaid", userIdPaid.toString(),
                "userIdPayer", userIdPayer.toString(),
                "money", money,
                "payDate", payDate.toString(),
                "description", description
        );
    }
}