package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetCurrencyByCountryCodeQueryResponse implements QueryResponse {
    @Getter private final String code;
    @Getter private final String symbol;
}
