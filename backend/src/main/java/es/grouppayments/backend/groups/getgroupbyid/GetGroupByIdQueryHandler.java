package es.grouppayments.backend.groups.getgroupbyid;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.query.QueryHandler;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetGroupByIdQueryHandler implements QueryHandler<GetGroupByIdQuery, GetGroupByIdQueryResponse> {
    private final GroupService groupService;

    @Override
    public GetGroupByIdQueryResponse handle(GetGroupByIdQuery getGroupByIdQuery) {
        Group group = groupService.findByIdOrThrowException(getGroupByIdQuery.getGroupId());

        return new GetGroupByIdQueryResponse(group);
    }
}
