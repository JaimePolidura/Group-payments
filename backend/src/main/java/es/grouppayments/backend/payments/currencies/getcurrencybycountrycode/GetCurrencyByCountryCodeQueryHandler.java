package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.grouppayments.backend.payments.currencies._shared.domain.Currency;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public final class GetCurrencyByCountryCodeQueryHandler implements QueryHandler<GetCurrencyByCountryCodeQuery, GetCurrencyByCountryCodeQueryResponse> {
    private final CurrencyService currencyService;

    @Override
    public GetCurrencyByCountryCodeQueryResponse handle(GetCurrencyByCountryCodeQuery query) {
        Currency currency = this.currencyService.getByCountryCode(query.getCountryCode());

        return new GetCurrencyByCountryCodeQueryResponse(
                currency.getCode(),
                currency.getSymbol()
        );
    }
}
