package es.grouppayments.backend.payments.userpaymentsinfo.register.newregister;


import es.grouppayments.backend._shared.infrastructure.ApplicationController;
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
    public ResponseEntity<?> register(@RequestBody Request request){
        this.commandBus.dispatch(new RegisterWithStripeCommand(
                getLoggedUsername(),
                request.paymentMethod
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static class Request {
        public final String paymentMethod;
    }
}
