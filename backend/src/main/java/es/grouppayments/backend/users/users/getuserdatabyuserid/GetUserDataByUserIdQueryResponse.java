package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetUserDataByUserIdQueryResponse implements QueryResponse {
    @Getter private final String username;
    @Getter private final String email;
    @Getter private final int userImageId;
}
