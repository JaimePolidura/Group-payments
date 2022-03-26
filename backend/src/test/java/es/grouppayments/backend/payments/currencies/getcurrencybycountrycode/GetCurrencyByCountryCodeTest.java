package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.grouppayments.backend.payments.currencies._shared.domain.Currency;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public final class GetCurrencyByCountryCodeTest extends GetCurrencyByCountryCodeTestMother{
    @Before
    public void setup(){
        super.currencyRepository().save(new Currency(
                "EUR", "€", "ES"
        ));
    }

    @Test
    public void shoudlFindCurrency(){
        assertNotNull(super.execute(new GetCurrencyByCountryCodeQuery("es")));
        assertNotNull(super.execute(new GetCurrencyByCountryCodeQuery("ES")));
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntFindCurrency(){
        super.execute(new GetCurrencyByCountryCodeQuery("sdas"));
    }
}
