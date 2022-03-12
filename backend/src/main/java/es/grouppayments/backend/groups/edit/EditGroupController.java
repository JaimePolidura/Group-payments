package es.grouppayments.backend.groups.edit;

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
public class EditGroupController extends ApplicationController {
    private final CommandBus commandBus;

    @PostMapping("/groups/edit")
    public ResponseEntity<?> editGroup(@RequestBody Request request){
        this.commandBus.dispatch(new EditGroupCommand(
                UUID.fromString(request.groupId),
                getLoggedUsername(),
                request.newMoney,
                request.newDescription
        ));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static class Request {
        public final String groupId;
        public final String newDescription;
        public final double newMoney;
    }
}
