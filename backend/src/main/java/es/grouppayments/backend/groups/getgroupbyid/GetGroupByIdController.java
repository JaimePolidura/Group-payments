package es.grouppayments.backend.groups.getgroupbyid;


import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class GetGroupByIdController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("groups/id/{groupId}")
    public ResponseEntity<Response> getGroupById(@PathVariable String groupId){
        GetGroupByIdQueryResponse response = queryBus.ask(new GetGroupByIdQuery(UUID.fromString(groupId)));

        return buildNewHttpResponseOK(new Response(response.getGroup()));
    }

    @AllArgsConstructor
    private static class Response {
        public final Group group;
    }
}
