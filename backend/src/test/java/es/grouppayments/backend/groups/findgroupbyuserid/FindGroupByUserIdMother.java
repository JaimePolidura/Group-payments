package es.grouppayments.backend.groups.findgroupbyuserid;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class FindGroupByUserIdMother extends GroupTestMother {
    private final FindGroupByUserQueryHandler findGroupByUserQueryHandler;

    public FindGroupByUserIdMother(){
        this.findGroupByUserQueryHandler = new FindGroupByUserQueryHandler(
                new GroupMemberService(groupMemberRepository(), testEventBus),
                new GroupService(groupRepository(), testEventBus)
        );
    }

    public FindGroupByUserQueryResponse executeQuery(UUID userId){
        return this.findGroupByUserQueryHandler.handle(new FindGroupByUserQuery(userId));
    }
}
