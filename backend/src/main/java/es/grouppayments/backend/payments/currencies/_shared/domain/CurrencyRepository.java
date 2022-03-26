package es.grouppayments.backend.payments.currencies._shared.domain;

import java.util.Optional;

public interface CurrencyRepository {
    Optional<Currency> findCurrencyByCountryCode(String countryCode);
}
