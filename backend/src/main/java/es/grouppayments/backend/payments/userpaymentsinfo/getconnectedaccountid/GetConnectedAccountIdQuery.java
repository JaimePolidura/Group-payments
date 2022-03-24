package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetConnectedAccountIdQuery implements Query {
    @Getter private final UUID userId;
}
