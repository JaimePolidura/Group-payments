package es.grouppayments.backend.invitations.getbyuserid;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin
public final class GetInvitationsByUserIdController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/invitations/getbyuserid")
    public ResponseEntity<GetInvitationsByUserIdQueryResponse> getInvitationsByUserId() {
        GetInvitationsByUserIdQueryResponse response = this.queryBus.ask(new GetInvitationsByUserIdQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(response);
    }
}
