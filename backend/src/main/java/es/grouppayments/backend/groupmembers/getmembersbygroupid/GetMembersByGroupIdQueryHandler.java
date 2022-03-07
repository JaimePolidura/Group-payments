package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetMembersByGroupIdQueryHandler implements QueryHandler<GetMembersByGroupIdQuery, GetMembersByGroupIdQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final UsersService usersService;

    @Override
    public GetMembersByGroupIdQueryResponse handle(GetMembersByGroupIdQuery query) {
        ensureUserIdInGroup(query.getUserId(), query.getGroupId());

        List<GroupMember> groupMembers = groupMemberService.findMembersByGroupId(query.getGroupId());
        List<User> users = groupMembers.stream()
                .map(groupMember -> usersService.findByUserId(groupMember.getUserId()).get())
                .toList();

        return GetMembersByGroupIdQueryResponse.fromAggregateUserList(users);
    }

    private void ensureUserIdInGroup(UUID userId, UUID groupId){
        this.groupMemberService.findMembersByGroupId(groupId).stream()
                .filter(member -> member.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("You dont belong to that group"));
    }
}
