package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class GetMembersByGroupIdQueryResponse implements QueryResponse {
    private final List<GroupMemberUser> users;

    public static GetMembersByGroupIdQueryResponse fromAggregateUserList(List<User> usersAggregateList){
        return new GetMembersByGroupIdQueryResponse(usersAggregateList.stream()
                .map(GroupMemberUser::fromUserAggregate)
                .toList());
    }

    @AllArgsConstructor
    private static class GroupMemberUser {
        @Getter private final UUID userId;
        @Getter private final String username;
        @Getter private final String email;

        public static GroupMemberUser fromUserAggregate(User userAggregate){
            return new GroupMemberUser(userAggregate.getUserId(), userAggregate.getUsername(), userAggregate.getEmail());
        }
    }
}
