package es.grouppayments.backend.payments.userpaymentsinfo.changecard.prepare;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@CrossOrigin
@RestController
public final class PrepareChangeCardController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/payments/stripe/changecard/prepare")
    public ResponseEntity<?> changeCardPrepare(@RequestBody Request request){
        this.commandBus.dispatch(new PrepareChangeCardCommand(
                getLoggedUserId(),
                request.paymentMethod,
                request.dob
        ));

        return buildNewHttpResponseOK("An email has been send");
    }

    @AllArgsConstructor
    private static class Request {
        public final String paymentMethod;
        public final Dob dob;
    }
}
