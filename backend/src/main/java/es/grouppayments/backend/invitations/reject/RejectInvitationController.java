package es.grouppayments.backend.invitations.reject;


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
public final class RejectInvitationController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/invitations/reject")
    public ResponseEntity<?> rejectInvitation(@RequestBody Request request){
        this.commandBus.dispatch(new RejectInvitationCommand(
                request.invitationId,
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static final class Request {
        public final UUID invitationId;
        public final String ignoreThis;
    }
}
