package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdQueryResponse;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin
public class GetCurrentGroupByUserIdController extends ApplicationController {
    private final QueryBus queryBus;
    
    @GetMapping("groups/current")
    public ResponseEntity<Response> currentGroup(){
        GetCurrentGroupByUserQueryResponse response = queryBus.ask(new GetCurrentGroupByUserQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(new Response(
                response.getGroup().toPrimitives(),
                response.getMembers()
        ));
    }

    @AllArgsConstructor
    private static class Response {
        public Map<String, Object> group;
        public List<GetMemberByUserIdQueryResponse> members;
    }
}
