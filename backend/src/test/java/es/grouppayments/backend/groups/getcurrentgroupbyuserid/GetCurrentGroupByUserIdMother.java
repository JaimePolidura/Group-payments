package es.grouppayments.backend.groups.getcurrentgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.users.users._shared.application.UsersService;

import java.util.UUID;

public class GetCurrentGroupByUserIdMother extends GroupTestMother {
    private final GetCurrentGroupByUserQueryHandler findGroupByUserQueryHandler;

    public GetCurrentGroupByUserIdMother(){
        this.findGroupByUserQueryHandler = new GetCurrentGroupByUserQueryHandler(
                new GroupMemberService(groupMemberRepository(), testEventBus),
                new GroupService(groupRepository(), testEventBus),
                new UsersService(super.usersRepository(), super.testEventBus)
        );
    }

    public GetCurrentGroupByUserQueryResponse executeQuery(UUID userId){
        return this.findGroupByUserQueryHandler.handle(new GetCurrentGroupByUserQuery(userId));
    }
}
