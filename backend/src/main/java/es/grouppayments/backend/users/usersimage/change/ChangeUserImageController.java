package es.grouppayments.backend.users.usersimage.change;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend.users.users.getuserdatabyuserid.GetUserDataByUserIdQuery;
import es.grouppayments.backend.users.users.getuserdatabyuserid.GetUserDataByUserIdQueryResponse;
import es.grouppayments.backend.users.usersimage.get.GetUserImageQuery;
import es.grouppayments.backend.users.usersimage.get.GetUserImageQueryResponse;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class ChangeUserImageController extends ApplicationController {
    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping("/usersimage/change")
    public ResponseEntity<ChangeUserImageResponse> changeUserImage(@RequestBody byte[] imageBytes){
        this.commandBus.dispatch(new ChangeUserImageCommand(
                getLoggedUserId(),
                imageBytes
        ));
        GetUserDataByUserIdQueryResponse response = this.queryBus.ask(new GetUserDataByUserIdQuery(
                getLoggedUserId()
        ));

        return buildNewHttpResponseOK(new ChangeUserImageResponse(
                response.getUserImageId()
        ));
    }

    @AllArgsConstructor
    private static class ChangeUserImageResponse {
        @Getter private final int imageId;
    }
}
