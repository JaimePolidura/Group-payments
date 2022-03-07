package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Data;

import java.util.UUID;

@Data
public class GetMembersByGroupIdQuery implements Query {
    private final UUID groupId;
    private final UUID userId;
}
