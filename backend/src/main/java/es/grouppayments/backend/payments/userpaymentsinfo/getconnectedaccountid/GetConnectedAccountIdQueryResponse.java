package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountid;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetConnectedAccountIdQueryResponse implements QueryResponse {
    @Getter private final String connectedAcccountId;
}
