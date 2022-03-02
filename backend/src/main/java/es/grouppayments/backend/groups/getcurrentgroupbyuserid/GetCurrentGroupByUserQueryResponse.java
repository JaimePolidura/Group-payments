package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

@Data
public class GetCurrentGroupByUserQueryResponse implements QueryResponse {
    private final Group group;
}
