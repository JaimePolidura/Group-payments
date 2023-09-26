package es.grouppayments.backend.groupmembers.kick;

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
@AllArgsConstructor
@CrossOrigin
public class KickGroupMemberController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/groups/kick")
    public ResponseEntity<?> kickMember(@RequestBody Request request){
        this.commandBus.dispatch(new KickGroupMemberCommand(
                getLoggedUserId(),
                UUID.fromString(request.groupId),
                UUID.fromString(request.userIdToKick)
        ));

        return buildNewHttpResponseOK();
    }

    private static class Request{
        public String groupId;
        public String userIdToKick;
    }
}
