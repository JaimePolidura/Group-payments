package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.UsersService;

import java.util.UUID;

public class FindMembersByGroupIdMother extends TestMother {
    private final FindMembersByGroupIdQueryHandler handler;

    public FindMembersByGroupIdMother(){
        this.handler = new FindMembersByGroupIdQueryHandler(
                new GroupMemberService(groupMemberRepository, testEventBus),
                new UsersService(userRepository)
        );
    }

    public FindMembersByGroupIdQueryResponse executeQuery(UUID gruopId){
        return this.handler.handle(new FindMembersByGroupIdQuery(gruopId));
    }
}
