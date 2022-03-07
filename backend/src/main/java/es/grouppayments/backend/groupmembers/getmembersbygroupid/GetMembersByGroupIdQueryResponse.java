package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdQueryResponse;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetMembersByGroupIdQueryResponse implements QueryResponse {
    private final List<GetMemberByUserIdQueryResponse> members;

    public static GetMembersByGroupIdQueryResponse fromAggregateUserList(List<User> usersAggregateList){
        return new GetMembersByGroupIdQueryResponse(usersAggregateList.stream()
                .map(GetMemberByUserIdQueryResponse::fromUserAggregate)
                .toList());
    }
}
