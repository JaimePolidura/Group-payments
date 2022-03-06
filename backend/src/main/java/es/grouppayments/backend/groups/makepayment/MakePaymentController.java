package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend._shared.infrastructure.Controller;
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
public class MakePaymentController extends Controller {
    private final CommandBus commandBus;

    @PostMapping("/groups/makepayment")
    public ResponseEntity<?> makePayment(@RequestBody Request request){
        this.commandBus.dispatch(new MakePaymentCommand(
                UUID.fromString(request.groupId),
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK();
    }

    private static class Request {
        public String groupId;
    }
}
