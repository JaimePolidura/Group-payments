package es.grouppayments.backend.users.userexistsbyemail;

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
public final class UserExistsByEmailController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/users/existsbyemail")
    public ResponseEntity<UserExistsByEmailQueryResponse> existsByEmail(@RequestParam String email){
        return buildNewHttpResponseOK(this.queryBus.ask(new UserExistsByEmailQuery(email)));
    }
}
