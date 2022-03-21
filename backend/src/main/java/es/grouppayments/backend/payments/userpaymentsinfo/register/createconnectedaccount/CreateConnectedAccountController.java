package es.grouppayments.backend.payments.userpaymentsinfo.register.createconnectedaccount;

import com.stripe.model.Account;
import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class CreateConnectedAccountController extends ApplicationController {
    private final StripeService stripeService;

    @PostMapping("/payments/stripe/createconnectedaccount")
    public ResponseEntity<CreateConnectedAccountResponse> createCustomer(){
        Account account = stripeService.createConnectedAccount(getLoggedUsername());
        String accountLink = stripeService.createConnectedAccountLink(getLoggedUsername(), account.getId());

        return buildNewHttpResponseOK(new CreateConnectedAccountResponse(accountLink));
    }

    @AllArgsConstructor
    private static class CreateConnectedAccountResponse {
        public final String accountLink;
    }
}
