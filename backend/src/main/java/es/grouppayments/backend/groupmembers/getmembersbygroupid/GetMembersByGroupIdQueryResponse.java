package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.groupmembers.getmemberbyuserid.GetMemberByUserIdResponse;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.Data;

import java.util.List;

@Data
public class GetMembersByGroupIdQueryResponse implements QueryResponse {
    private final List<GetMemberByUserIdResponse> members;

    public static GetMembersByGroupIdQueryResponse fromAggregateUserList(List<User> usersAggregateList){
        return new GetMembersByGroupIdQueryResponse(usersAggregateList.stream()
                .map(GetMemberByUserIdResponse::fromUserAggregate)
                .toList());
    }
}
