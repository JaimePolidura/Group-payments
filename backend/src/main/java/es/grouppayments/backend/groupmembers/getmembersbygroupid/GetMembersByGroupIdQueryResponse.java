package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
public class GetMembersByGroupIdQueryResponse implements QueryResponse {
    private final List<GroupMemberUser> members;

    public static GetMembersByGroupIdQueryResponse fromAggregateUserList(List<User> usersAggregateList){
        return new GetMembersByGroupIdQueryResponse(usersAggregateList.stream()
                .map(GroupMemberUser::fromUserAggregate)
                .toList());
    }

    @AllArgsConstructor
    public static class GroupMemberUser {
        @Getter private final UUID userId;
        @Getter private final String username;
        @Getter private final String email;
        @Getter private final String photoUrl;

        public static GroupMemberUser fromUserAggregate(User userAggregate){
            return new GroupMemberUser(
                    userAggregate.getUserId(),
                    userAggregate.getUsername(),
                    userAggregate.getEmail(),
                    userAggregate.getPhotoUrl()
            );
        }
    }
}
