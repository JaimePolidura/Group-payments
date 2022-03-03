package es.grouppayments.backend.groups.getgroupbyid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Data;

import java.util.UUID;

@Data
public class GetGroupByIdQuery implements Query {
    private final UUID groupId;
}
