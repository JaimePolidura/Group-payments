package es.grouppayments.backend.groups.findgroupbyuserid;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

@Data
public class FindGroupByUserQueryResponse implements QueryResponse {
    private final Group group;
}
