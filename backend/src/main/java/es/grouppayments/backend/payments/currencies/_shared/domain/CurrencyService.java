package es.grouppayments.backend.payments.currencies._shared.domain;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public Currency getByCountryCode(String countryCode){
        return this.currencyRepository.findCurrencyByCountryCode(countryCode)
                .orElseThrow(() -> new ResourceNotFound("Currency not suported"));
    }
}
