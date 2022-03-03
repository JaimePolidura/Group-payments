package es.grouppayments.backend.groups.getgroupbyid;

import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class GetGroupByIdTestMother extends GroupTestMother {
    private final GetGroupByIdQueryHandler handler;

    public GetGroupByIdTestMother() {
        this.handler = new GetGroupByIdQueryHandler(
                new GroupService(super.groupRepository(), testEventBus)
        );
    }

    public GetGroupByIdQueryResponse executeQuery(UUID groupId){
        return handler.handle(new GetGroupByIdQuery(groupId));
    }
}
