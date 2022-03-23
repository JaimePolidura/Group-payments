package es.grouppayments.backend.payments.userpaymentsinfo.register.createconnectedaccountlink;


import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class CreateConnectedAccountLinkController extends ApplicationController {
    private final StripeService stripeService;

    @PostMapping("/payments/stripe/createlink")
    public ResponseEntity<Response> createlink () {
        String link = stripeService.createConnectedAccountLink(getLoggedUsername());

        return buildNewHttpResponseOK(new Response(link));
    }

    @AllArgsConstructor
    private static class Response {
        public final String link;
    }

}
