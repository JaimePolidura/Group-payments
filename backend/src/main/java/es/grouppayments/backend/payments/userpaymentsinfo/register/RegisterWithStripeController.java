package es.grouppayments.backend.payments.userpaymentsinfo.register;


import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink.GetConnectedAccountLinkQuery;
import es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink.GetConnectedAccountLinkQueryResponse;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class RegisterWithStripeController extends ApplicationController {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/payments/stripe/register")
    public ResponseEntity<GetConnectedAccountLinkQueryResponse> register(@RequestBody Request request){
        this.commandBus.dispatch(new RegisterWithStripeCommand(
                getLoggedUsername(),
                request.paymentMethod
        ));

        GetConnectedAccountLinkQueryResponse response = this.queryBus.ask(new GetConnectedAccountLinkQuery(
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    private static class Request {
        public final String paymentMethod;
        private final String ignoreThis;
    }
}
