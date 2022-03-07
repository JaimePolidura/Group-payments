package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.grouppayments.backend._shared.infrastructure.Controller;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class GetMemberByUserIdController extends Controller {
    private final QueryBus queryBus;

    @GetMapping("/groups/member")
    public ResponseEntity<GetMemberByUserIdQueryResponse> getMember(@RequestParam String userId,
                                                                    @RequestParam String groupId){

        GetMemberByUserIdQueryResponse queryResponse = this.queryBus.ask(new GetMemberByUserIdQuery(
                UUID.fromString(userId),
                UUID.fromString(groupId),
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(queryResponse);
    }
}
