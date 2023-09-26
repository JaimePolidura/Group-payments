package es.grouppayments.backend.payments.payments.getcommissionpolicy;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetCommissionPolicyQueryResponse implements QueryResponse {
    @Getter private final double commission;
}
