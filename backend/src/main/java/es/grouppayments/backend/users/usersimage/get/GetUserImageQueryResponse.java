package es.grouppayments.backend.users.usersimage.get;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetUserImageQueryResponse implements QueryResponse {
    @Getter private final byte[] content;
}
