package es.grouppayments.backend.groups.getgroupbyid;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

@Data
public class GetGroupByIdQueryResponse implements QueryResponse {
    private final Group group;
}
