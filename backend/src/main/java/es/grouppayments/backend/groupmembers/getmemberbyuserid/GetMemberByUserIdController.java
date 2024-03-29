package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class GetMemberByUserIdController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/groups/member")
    public ResponseEntity<Response> getMember(@RequestParam String userId,
                                              @RequestParam String groupId){
        GetMemberByUserIdQueryResponse queryResponse = this.queryBus.ask(new GetMemberByUserIdQuery(
                UUID.fromString(userId),
                UUID.fromString(groupId),
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(new Response(queryResponse));
    }

    @AllArgsConstructor
    private static class Response {
        public final GetMemberByUserIdQueryResponse member;
    }
}
