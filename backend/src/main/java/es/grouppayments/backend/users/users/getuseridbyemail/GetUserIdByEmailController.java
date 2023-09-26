package es.grouppayments.backend.users.users.getuseridbyemail;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@AllArgsConstructor
public final class GetUserIdByEmailController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/users/getuseridbyemail")
    public ResponseEntity<GetUserIdByEmailQueryResponse> existsByEmail(@RequestParam String email){
        return buildNewHttpResponseOK(this.queryBus.ask(new GetUserIdByEmailQuery(email)));
    }
}
