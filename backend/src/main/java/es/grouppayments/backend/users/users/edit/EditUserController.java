package es.grouppayments.backend.users.users.edit;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//13 min en implementar use cases
//20 min en implementar test cases
//50 min en implementar * en frontend
@RestController
@CrossOrigin
@AllArgsConstructor
public final class EditUserController extends ApplicationController {
    private final CommandBus commandBus;

    @SneakyThrows
    @PostMapping("/users/edit")
    public ResponseEntity<Response> editUser(@RequestBody Request request) {
        this.commandBus.dispatch(new EditUserCommand(
                getLoggedUserId(),
                request.username,
                request.currency,
                request.countryCode
        ));

        return buildNewHttpResponseOK(new Response("User edited"));
    }

    @AllArgsConstructor
    private static final class Response {
        public final String status;
    }

    @AllArgsConstructor
    private static final class Request {
        public final String username;
        public final Currency currency;
        public final String countryCode;
    }
}
