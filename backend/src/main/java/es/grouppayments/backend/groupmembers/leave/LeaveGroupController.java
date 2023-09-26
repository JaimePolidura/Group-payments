package es.grouppayments.backend.groupmembers.leave;

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
public class LeaveGroupController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/groups/leave")
    public ResponseEntity<?> leaveGroup(@RequestBody Request request){
        this.commandBus.dispatch(new LeaveGroupCommand(
                UUID.fromString(request.groupId),
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static class Request{
        public final String groupId;
        public final String ignoreThis;
    }
}
