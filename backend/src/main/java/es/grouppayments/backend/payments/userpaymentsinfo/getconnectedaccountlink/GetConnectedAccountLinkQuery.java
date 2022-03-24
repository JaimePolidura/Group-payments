package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetConnectedAccountLinkQuery implements Query {
    @Getter private final UUID userId;
}
