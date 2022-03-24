package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;


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
public final class GetConnectedAccountLinkController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/payments/stripe/getconnectedaccountlink")
    public ResponseEntity<GetConnectedAccountLinkQueryResponse> getConnectedAccountLink(){
        GetConnectedAccountLinkQueryResponse response = this.queryBus.ask(new GetConnectedAccountLinkQuery(
                getLoggedUsername())
        );

        return buildNewHttpResponseOK(response);
    }
}
