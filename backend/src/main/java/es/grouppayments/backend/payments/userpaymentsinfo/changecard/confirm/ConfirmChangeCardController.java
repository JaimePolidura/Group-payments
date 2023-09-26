package es.grouppayments.backend.payments.userpaymentsinfo.changecard.confirm;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
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
public final class ConfirmChangeCardController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/payments/stripe/changecard/confirm")
    public ResponseEntity<?> confirmChangeCard(@RequestBody Request request){
        this.commandBus.dispatch(new ConfirmChangeCardCommand(request.token));

        return buildNewHttpResponseOK("Account changed login again");
    }


    @AllArgsConstructor
    private static final class Request {
        public final String token;
        public final String ignoreThis;
    }
}
