package es.grouppayments.backend.payments.paymentshistory.getpaymentshistory;

import es.grouppayments.backend.payments.paymentshistory._shared.domain.Payment;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public final class GetPaymentsHistoryQueryResponse implements QueryResponse {
    @Getter private final List<Payment> payments;
}
