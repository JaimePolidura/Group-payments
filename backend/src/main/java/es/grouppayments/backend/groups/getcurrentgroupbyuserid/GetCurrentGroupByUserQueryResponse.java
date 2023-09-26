package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdQueryResponse;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GetCurrentGroupByUserQueryResponse implements QueryResponse {
   @Getter private final Group group;
   @Getter private final List<GetMemberByUserIdQueryResponse> members;
}
