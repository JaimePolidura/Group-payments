package es.grouppayments.backend.payments.currencies.getcurrencybycountrycode;

import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class GetCurrencyByCountryCodeController {
    private final QueryBus queryBus;

    @GetMapping("/payments/currencies/getbycountrycode")
    public ResponseEntity<GetCurrencyByCountryCodeQueryResponse> getByCountryCode(@RequestParam String countryCode){
        return this.queryBus.ask(new GetCurrencyByCountryCodeQuery(countryCode));
    }
}
