package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@CrossOrigin
public final class GetUserDataByUserIdController extends ApplicationController {
    private final QueryBus queryBus;
    
    @GetMapping("/users/getuserdatabyuserid")
    public ResponseEntity<GetUserDataByUserIdQueryResponse> getUsernameByUserId(@RequestParam String userId){
        return buildNewHttpResponseOK(this.queryBus.ask(new GetUserDataByUserIdQuery(userId)));
    }
}
