package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetCurrentGroupByUserQueryHandler implements QueryHandler<GetCurrentGroupByUserQuery, GetCurrentGroupByUserQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;

    @Override
    public GetCurrentGroupByUserQueryResponse handle(GetCurrentGroupByUserQuery findByUserQuery) {
        UUID groupId = groupMemberService.findGroupMemberByUserIdOrThrowException(findByUserQuery.getUserId())
                .getGroupId();

        return new GetCurrentGroupByUserQueryResponse(groupService.findByIdOrThrowException(groupId));
    }
}
