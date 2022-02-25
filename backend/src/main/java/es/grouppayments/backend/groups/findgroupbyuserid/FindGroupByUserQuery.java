package es.grouppayments.backend.groups.findgroupbyuserid;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.Data;

import java.util.UUID;

@Data
public class FindGroupByUserQuery implements Query {
    private final UUID userId;
}
