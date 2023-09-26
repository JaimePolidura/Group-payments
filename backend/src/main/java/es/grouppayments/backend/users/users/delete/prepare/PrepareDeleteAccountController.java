package es.grouppayments.backend.users.users.delete.prepare;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class PrepareDeleteAccountController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/users/delete/prepare")
    public ResponseEntity<Response> prepareUserDelete(){
        this.commandBus.dispatch(new PrepareDeleteAccountCommand(getLoggedUserId()));

        return buildNewHttpResponseOK(new Response("An email has been sended"));
    }

    @AllArgsConstructor
    private static final class Response {
        public final String response;
    }
}
