package es.grouppayments.backend.payments.payments.getcommissionpolicy;


import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public final class GetCommissionPolicyController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/payments/commissionpolicy")
    public ResponseEntity<GetCommissionPolicyQueryResponse> getCommissionPolicy(){
        return buildNewHttpResponseOK(this.queryBus.ask(new GetCommissionPolicyQuery()));
    }
}
