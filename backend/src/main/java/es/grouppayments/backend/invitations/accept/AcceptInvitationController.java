package es.grouppayments.backend.invitations.accept;

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

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class AcceptInvitationController extends ApplicationController {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/invitations/accept")
    public ResponseEntity<GetCurrentGroupByUserQueryResponse> acceptInvitation(@RequestBody Request request){
        this.commandBus.dispatch(new AcceptInvitationCommand(
                request.invitationId,
                getLoggedUserId()
        ));

        GetCurrentGroupByUserQueryResponse currentGroup = queryBus.ask(new GetCurrentGroupByUserQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(currentGroup);
    }

    @AllArgsConstructor
    private static final class Request {
        public final UUID invitationId;
        public final String ignoreThis;
    }
}
