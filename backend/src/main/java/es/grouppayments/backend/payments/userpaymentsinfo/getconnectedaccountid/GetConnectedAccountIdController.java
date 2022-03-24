package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountid;


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
public final class GetConnectedAccountIdController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/payments/stripe/getconnectedaccountid")
    public ResponseEntity<GetConnectedAccountIdQueryResponse> getConnectedAccountId(){
        GetConnectedAccountIdQueryResponse response = this.queryBus.ask(new GetConnectedAccountIdQuery(
                getLoggedUsername()
        ));

        return buildNewHttpResponseOK(response);
    }
}
