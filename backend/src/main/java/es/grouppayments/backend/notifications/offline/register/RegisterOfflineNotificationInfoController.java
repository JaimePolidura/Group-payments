package es.grouppayments.backend.notifications.offline.register;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class RegisterOfflineNotificationInfoController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/notifications/offline/register")
    public ResponseEntity register(@RequestBody Request request){
        this.commandBus.dispatch(new RegisterOfflineNotificationInfoCommand(
                getLoggedUserId(),
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
