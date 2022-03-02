package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
@CrossOrigin
public class GetCurrentGroupByUserIdController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("groups/current")
    public ResponseEntity<Response> currentGroup(){
        GetCurrentGroupByUserQueryResponse queryResponse = queryBus.ask(new GetCurrentGroupByUserQuery(
                UUID.fromString(getLoggedUsername()))
        );

        return buildNewHttpResponseOK(new Response(queryResponse.getGroup()));
    }

    @AllArgsConstructor
    private static class Response {
        public Group group;
    }
}
