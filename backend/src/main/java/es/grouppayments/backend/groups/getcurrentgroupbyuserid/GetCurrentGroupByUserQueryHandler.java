package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetCurrentGroupByUserQueryHandler implements QueryHandler<GetCurrentGroupByUserQuery, GetCurrentGroupByUserQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;
    private final UsersService usersService;

    @Override
    public GetCurrentGroupByUserQueryResponse handle(GetCurrentGroupByUserQuery query) {
        UUID groupId = groupMemberService.findGroupMemberByUserIdOrThrowException(query.getUserId())
                .getGroupId();

        List<GroupMember> groupMembers = groupMemberService.findMembersByGroupId(groupId);
        List<User> users = groupMembers.stream()
                .map(groupMember -> usersService.getByUserId(groupMember.getUserId()))
                .toList();

        return new GetCurrentGroupByUserQueryResponse(
                groupService.findByIdOrThrowException(groupId),
                MembersOfGroupQueryResponse.fromAggregateUserList(users).getMembers()
        );
    }
}
