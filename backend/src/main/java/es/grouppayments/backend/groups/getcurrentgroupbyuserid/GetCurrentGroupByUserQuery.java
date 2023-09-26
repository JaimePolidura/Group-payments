package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Data;

import java.util.UUID;

@Data
public class GetCurrentGroupByUserQuery implements Query {
    private final UUID userId;
}
