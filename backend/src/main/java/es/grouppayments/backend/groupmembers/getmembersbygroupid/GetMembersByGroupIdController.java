package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class GetMembersByGroupIdController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/groups/members/{groupId}")
    public ResponseEntity<?> getMembers(@PathVariable String groupId){
        GetMembersByGroupIdQueryResponse queryResponse = queryBus.ask(new GetMembersByGroupIdQuery(
                UUID.fromString(groupId))
        );

        return buildNewHttpResponseOK(queryResponse.getUsers());
    }

    private static class Response {
        public List<User> members;
    }
}
