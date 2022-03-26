package es.grouppayments.backend.payments.currencies;

import _shared.TestMother;
import _shared.UsingCurrencies;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;
import es.grouppayments.backend.payments.currencies._shared.infrastructure.CurrencyRepositoryInMemory;

public class CurrenciesTestMother extends TestMother implements UsingCurrencies {
    private final CurrencyRepository currencyRepository;

    public CurrenciesTestMother(){
        this.currencyRepository = new CurrencyRepositoryInMemory();
    }

    @Override
    public CurrencyRepository currencyRepository() {
        return this.currencyRepository;
    }
}
