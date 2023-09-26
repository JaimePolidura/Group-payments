package es.grouppayments.backend.users.users.delete.confirm;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class ConfirmDeleteAccountController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/users/delete/confirm")
    public ResponseEntity<?> confirmUserDelete(@RequestBody Request request){
        this.commandBus.dispatch(new ConfirmDeleteAccountCommand(
                request.token
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static final class Request {
        public final String token;
        public final String ignoreThis;
    }
}
