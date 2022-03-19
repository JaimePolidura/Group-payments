package es.grouppayments.backend.payments.createcustomer;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class CreateCustomerController extends ApplicationController {
    private final StripeService stripeService;

    @PostMapping("/payments/stripe/createcustomer")
    public ResponseEntity<String> createCustomer(@RequestBody CreateCustomerRequest request){
        stripeService.createCustomer(request.paymentMethod);

        return buildNewHttpResponseOK("Customer created");
    }

    @AllArgsConstructor
    private static class CreateCustomerRequest {
        public final String paymentMethod;
        public final String ignoreThis;
    }

}
