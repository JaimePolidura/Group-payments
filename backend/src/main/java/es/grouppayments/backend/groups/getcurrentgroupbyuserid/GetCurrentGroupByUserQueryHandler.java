package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetCurrentGroupByUserQueryHandler implements QueryHandler<GetCurrentGroupByUserQuery, GetCurrentGroupByUserQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;

    @Override
    public GetCurrentGroupByUserQueryResponse handle(GetCurrentGroupByUserQuery findByUserQuery) {
        Optional<UUID> optionalGorupId = groupMemberService.findGroupMemberByUserId(findByUserQuery.getUserId())
                .map(GroupMember::getGroupId);

        return optionalGorupId
                .map(uuid -> new GetCurrentGroupByUserQueryResponse(groupService.findById(uuid).get()))
                .orElseGet(() -> new GetCurrentGroupByUserQueryResponse(null));
    }
}
