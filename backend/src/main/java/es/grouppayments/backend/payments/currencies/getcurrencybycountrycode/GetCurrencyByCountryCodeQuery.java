package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetCurrencyByCountryCodeQuery implements Query {
    @Getter private final String countryCode;
}
