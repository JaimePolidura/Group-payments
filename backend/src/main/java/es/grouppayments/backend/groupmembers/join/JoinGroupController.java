package es.grouppayments.backend.groupmembers.join;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.groups.getcurrentgroupbyuserid.GetCurrentGroupByUserQuery;
import es.grouppayments.backend.groups.getcurrentgroupbyuserid.GetCurrentGroupByUserQueryResponse;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class JoinGroupController extends ApplicationController {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("groups/join")
    public ResponseEntity<Response> join(@RequestBody Request request){
        commandBus.dispatch(new JoinGroupCommand(
                getLoggedUserId(),
                UUID.fromString(request.groupId)
        ));

        GetCurrentGroupByUserQueryResponse queryResponse = queryBus.ask(new GetCurrentGroupByUserQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(new Response(queryResponse.getGroup().toPrimitives()));
    }

    @AllArgsConstructor
    private static class Request {
        public String groupId;
        public String ignoreThis;
    }

    @AllArgsConstructor
    private static class Response {
        public Map<String, Object> group;
    }
}
