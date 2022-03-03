package es.grouppayments.backend.groupmembers.join;

import es.grouppayments.backend._shared.infrastructure.Controller;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
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
public class JoinGroupController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("groupmembers/join")
    public ResponseEntity join(@RequestBody Request request){
        commandBus.dispatch(new JoinGroupCommand(UUID.fromString(request.userId), UUID.fromString(request.groupId)));

        return buildNewHttpResponseOK();
    }

    @AllArgsConstructor
    private static class Request {
        public String userId;
        public String groupId;
    }

    @AllArgsConstructor
    private static class Response {

    }
}
