package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Data;

import java.util.UUID;

@Data
public class FindMembersByGroupIdQuery implements Query {
    private final UUID groupId;
}
