package es.grouppayments.backend.groups.create;

import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups.getcurrentgroupbyuserid.GetCurrentGroupByUserQuery;
import es.grouppayments.backend.groups.getcurrentgroupbyuserid.GetCurrentGroupByUserQueryResponse;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CreateGroupController extends Controller {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/groups/create")
    public ResponseEntity<Response> createGroup(@RequestBody Request request){
        commandBus.dispatch(new CreateGroupCommand(
                UUID.randomUUID(),
                getLoggedUsername(),
                request.money,
                request.title)
        );

        GetCurrentGroupByUserQueryResponse currentGroupQueryResponse = queryBus.ask(new GetCurrentGroupByUserQuery(
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(new Response(currentGroupQueryResponse.getGroup()));
    }

    @AllArgsConstructor
    private static class Request {
        public String title;
        public double money;
    }

    @AllArgsConstructor
    private static class Response {
        public Group group;
    }
}
