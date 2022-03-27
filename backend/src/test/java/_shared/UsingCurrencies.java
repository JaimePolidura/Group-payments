package _shared;

import es.grouppayments.backend.payments.currencies._shared.domain.Currency;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;

public interface UsingCurrencies {
    CurrencyRepository currencyRepository();

    default void addCurrency(Currency currency){
        this.currencyRepository().save(currency);
    }
}
