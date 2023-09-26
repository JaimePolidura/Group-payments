package es.grouppayments.backend.users.users.getuseridbyemail;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetUserIdByEmailQueryResponse implements QueryResponse {
    @Getter private final UUID userId;
}
