package es.grouppayments.backend.users.usersimage.get;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@CrossOrigin
@AllArgsConstructor
public final class GetUserImageController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping(value = "/usersimage/get/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getUserIamgeById(@PathVariable int id){
        GetUserImageQueryResponse response = this.queryBus.ask(new GetUserImageQuery(id));

        return buildNewHttpResponseOK(response.getContent());
    }
}
