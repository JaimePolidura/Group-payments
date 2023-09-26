package es.grouppayments.backend.groups.create;

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

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CreateGroupController extends ApplicationController {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/groups/create")
    public ResponseEntity<GetCurrentGroupByUserQueryResponse> createGroup(@RequestBody Request request){
        commandBus.dispatch(new CreateGroupCommand(
                UUID.randomUUID(),
                getLoggedUserId(),
                request.money,
                request.title,
                request.usersEmailToInvite
        ));

        GetCurrentGroupByUserQueryResponse currentGroupQueryResponse = queryBus.ask(new GetCurrentGroupByUserQuery(
                getLoggedUserId())
        );

        return buildNewHttpResponseOK(currentGroupQueryResponse);
    }

    @AllArgsConstructor
    private static class Request {
        public List<String> usersEmailToInvite;
        public String title;
        public double money;
    }
}
