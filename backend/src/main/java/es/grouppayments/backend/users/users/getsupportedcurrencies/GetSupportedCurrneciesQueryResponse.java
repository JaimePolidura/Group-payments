package es.grouppayments.backend.users.users.getsupportedcurrencies;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public final class GetSupportedCurrneciesQueryResponse implements QueryResponse {
    @Getter private final List<CurrencyResponse> currencies;

    public static GetSupportedCurrneciesQueryResponse fromCurrenciesArray(Currency[] currencies){
        return new GetSupportedCurrneciesQueryResponse(Arrays.stream(currencies)
                .map(currency -> new CurrencyResponse(currency.name(), currency.symbol, currency.usingCountries))
                .toList()
        );
    }

    @AllArgsConstructor
    static final class CurrencyResponse {
        @Getter private final String code;
        @Getter private final String symbol;
        @Getter private final Set<String> usingCountries;
    }
}
