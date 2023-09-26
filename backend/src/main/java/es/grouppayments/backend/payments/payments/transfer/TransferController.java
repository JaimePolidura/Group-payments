package es.grouppayments.backend.payments.payments.transfer;

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
public final class TransferController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/payments/transfer")
    public ResponseEntity<?> transfer(@RequestBody Request request){
        this.commandBus.dispatch(new TransferCommand(
                getLoggedUserId(),
                UUID.fromString(request.to),
                request.money,
                request.description
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private final static class Request {
        public final String to;
        public final double money;
        public final String description;
    }
}
