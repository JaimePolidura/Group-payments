package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FindMembersByGroupIdQueryHandler implements QueryHandler<FindMembersByGroupIdQuery, FindMembersByGroupIdQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final UsersService usersService;

    @Override
    public FindMembersByGroupIdQueryResponse handle(FindMembersByGroupIdQuery query) {
        List<GroupMember> groupMembers = groupMemberService.findMembersByGroupId(query.getGroupId());
        List<User> users = groupMembers.stream()
                .map(groupMember -> usersService.findByUserId(groupMember.getUserId()).get())
                .toList();

        return new FindMembersByGroupIdQueryResponse(users);
    }
}
