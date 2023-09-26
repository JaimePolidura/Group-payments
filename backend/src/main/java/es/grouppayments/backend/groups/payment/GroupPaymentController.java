package es.grouppayments.backend.groups.payment;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin
@RestController
@AllArgsConstructor
public class GroupPaymentController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/groups/payment")
    public ResponseEntity<?> makePayment(@RequestBody Request request){
        this.commandBus.dispatch(new GroupPaymentCommand(
                UUID.fromString(request.groupId),
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK();
    }

    private static class Request {
        public String groupId;
    }
}
