package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetConnectedAccountLinkQueryResponse implements QueryResponse {
    @Getter private final String link;
}
