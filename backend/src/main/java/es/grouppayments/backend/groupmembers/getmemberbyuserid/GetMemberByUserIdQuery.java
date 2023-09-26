package es.grouppayments.backend.groupmembers.getmemberbyuserid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class GetMemberByUserIdQuery implements Query {
    @Getter private final UUID userIdToGet;
    @Getter private final UUID groupId;
    @Getter private final UUID userId;
}
