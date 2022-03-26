package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.grouppayments.backend.payments.currencies.CurrenciesTestMother;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;

public class GetCurrencyByCountryCodeTestMother extends CurrenciesTestMother {
    private final GetCurrencyByCountryCodeQueryHandler handler;

    public GetCurrencyByCountryCodeTestMother(){
        this.handler = new GetCurrencyByCountryCodeQueryHandler(
                new CurrencyService(super.currencyRepository())
        );
    }

    public GetCurrencyByCountryCodeQueryResponse execute(GetCurrencyByCountryCodeQuery query){
        return this.handler.handle(query);
    }
}
