package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
public final class GetPaymentsHistoryController extends ApplicationController {
    private final QueryBus queryBus;

    @GetMapping("/payments/paymentshistory")
    public ResponseEntity<GetPaymentsHistoryQueryResponse> getPaymentsHistory(@RequestParam int pageNumber,
                                                @RequestParam int itemsPerPage,
                                                @RequestParam SearchPaymentByType paymentTypeSearch){

        GetPaymentsHistoryQueryResponse response = this.queryBus.ask(new GetPaymentsHistoryQuery(
                getLoggedUsername(),
                pageNumber,
                itemsPerPage,
                paymentTypeSearch
        ));

        return buildNewHttpResponseOK(response);
    }
}
