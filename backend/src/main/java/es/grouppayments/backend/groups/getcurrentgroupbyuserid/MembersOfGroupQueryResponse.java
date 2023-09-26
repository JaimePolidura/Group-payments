package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdQueryResponse;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

import java.util.List;

@Data
public class MembersOfGroupQueryResponse implements QueryResponse {
    private final List<GetMemberByUserIdQueryResponse> members;

    public static MembersOfGroupQueryResponse fromAggregateUserList(List<User> usersAggregateList){
        return new MembersOfGroupQueryResponse(usersAggregateList.stream()
                .map(GetMemberByUserIdQueryResponse::fromUserAggregate)
                .toList());
    }
}
