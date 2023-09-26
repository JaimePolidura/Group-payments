package es.grouppayments.backend.users.users._shared.domain;

import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public enum Currency {
    EUR("€", Set.of("ES", "FR")),
    USD("$", Set.of("US"));

    public final String symbol;
    public final Set<String> usingCountries;

    Currency(String symbol, Set<String> usingCountries) {
        this.symbol = symbol;
        this.usingCountries = usingCountries;
    }

    @Override
    public String toString() {
        return this.toJSON();
    }

    public String toJSON(){
        return new JSONObject(this.toMap()).toString();
    }

    public Map<String, Object> toMap(){
        return Map.of(
                "code", this.name(),
                "symbol", this.symbol
        );
    }

    public static Currency getCurrencyByCountry(String country){
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.usingCountries.contains(country.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("Country not soported"));
    }
}
