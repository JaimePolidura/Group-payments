package es.grouppayments.backend.payments.userpaymentsinfo.register;


import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
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
                getLoggedUserId(),
                request.paymentMethod,
                new Dob(
                        request.dob.year,
                        request.dob.month,
                        request.dob.day
                )
        ));

        GetConnectedAccountLinkQueryResponse response = this.queryBus.ask(new GetConnectedAccountLinkQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(response);
    }

    @AllArgsConstructor
    private static class Request {
        public final String paymentMethod;
        public final RequestDob dob;
        private final String ignoreThis;
    }

    @AllArgsConstructor
    private static class RequestDob {
        public final long year;
        public final long day;
        public final long month;
    }
}
