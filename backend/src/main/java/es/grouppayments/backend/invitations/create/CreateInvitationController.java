package es.grouppayments.backend.invitations.create;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class CreateInvitationController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/invitations/create")
    public ResponseEntity<?> createInvitation(@RequestBody Request request){
        this.commandBus.dispatch(new CreateInvitationCommand(
                request.groupId,
                request.toUserId,
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static class Request {
        public final UUID groupId;
        public final UUID toUserId;
    }
}
