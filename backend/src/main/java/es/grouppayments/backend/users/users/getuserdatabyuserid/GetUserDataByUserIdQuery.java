package es.grouppayments.backend.users.users.getuserdatabyuserid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Getter;

import java.util.UUID;

public final class GetUserDataByUserIdQuery implements Query {
    @Getter private final UUID userId;

    public GetUserDataByUserIdQuery(String userId) {
        this.userId = UUID.fromString(userId);
    }

    public GetUserDataByUserIdQuery(UUID userId) {
        this.userId = userId;
    }
}
