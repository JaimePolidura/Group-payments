package es.grouppayments.backend.groups.findgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FindGroupByUserQueryHandler implements QueryHandler<FindGroupByUserQuery, FindGroupByUserQueryResponse> {
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;

    @Override
    public FindGroupByUserQueryResponse handle(FindGroupByUserQuery findByUserQuery) {
        Optional<UUID> optionalGorupId = groupMemberService.findGroupIdByUserId(findByUserQuery.getUserId());

        return optionalGorupId
                .map(uuid -> new FindGroupByUserQueryResponse(groupService.findById(uuid).get()))
                .orElseGet(() -> new FindGroupByUserQueryResponse(null));
    }
}
