package es.grouppayments.backend.users.users.getsupportedcurrencies;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import org.junit.Test;

public final class GetSupportedCurrenciesTest extends GetSupportedCurrenciesTestMother{
    @Test
    public void shouldGet(){
        GetSupportedCurrneciesQueryResponse response = super.execute();

        assertCollectionSize(response.getCurrencies(), Currency.values().length);

        Currency randomCurrnecy = this.getRandomCurrency();

        assertAnyMatch(response.getCurrencies(), currency -> currency.getCode().equals(randomCurrnecy.name())
                && currency.getSymbol().equals(randomCurrnecy.symbol)
                && currency.getUsingCountries().equals(randomCurrnecy.usingCountries));
    }

    private Currency getRandomCurrency(){
        return Currency.values()[(int) (Math.random() * Currency.values().length)];
    }
}
