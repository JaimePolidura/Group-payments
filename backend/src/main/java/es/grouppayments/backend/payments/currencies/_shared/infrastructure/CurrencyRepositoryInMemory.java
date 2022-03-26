package es.grouppayments.backend.payments.currencies._shared.infrastructure;

import es.grouppayments.backend.payments.currencies._shared.domain.Currency;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public final class CurrencyRepositoryInMemory implements CurrencyRepository {
    private final Map<String, Currency> countryCodeWithCurrencies;

    public CurrencyRepositoryInMemory(){
        this.countryCodeWithCurrencies = new HashMap<>(){{
            put("ES", new Currency("EUR", "€", "ES"));
        }};
    }

    @Override
    public Optional<Currency> findCurrencyByCountryCode(String countryCode) {
        return Optional.ofNullable(this.countryCodeWithCurrencies.get(countryCode.toUpperCase()));
    }
}
