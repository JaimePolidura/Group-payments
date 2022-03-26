package es.grouppayments.backend.payments.currencies._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public final class Currency extends Aggregate {
    @Getter private final String code;
    @Getter private final String symbol;
    @Getter private final String countryCode;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "code", code,
                "symbol", symbol,
                "countryCode", countryCode
        );
    }
}
