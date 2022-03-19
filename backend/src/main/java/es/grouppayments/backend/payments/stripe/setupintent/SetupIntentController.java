package es.grouppayments.backend.payments.stripe.setupintent;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class SetupIntentController extends ApplicationController {
    private final StripeService stripeService;

    @GetMapping("/payments/stripe/setupintent")
    public ResponseEntity<String> setupIntent(){
        return buildNewHttpResponseOK(stripeService.setupIntent());
    }
}
