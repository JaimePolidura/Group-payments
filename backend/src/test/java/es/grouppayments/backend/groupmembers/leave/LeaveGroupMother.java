package es.grouppayments.backend.groupmembers.leave;

import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class LeaveGroupMother extends GroupMembersTestMother {
    private final LeaveGroupCommandHandler handler;

    public LeaveGroupMother(){
        this.handler = new LeaveGroupCommandHandler(
                new GroupService(groupRepository(), super.testEventBus),
                new GroupMemberService(groupMemberRepository(), super.testEventBus),
                super.testEventBus
        );
    }

    public void execute(UUID userId, UUID groupId){
        this.handler.handle(new LeaveGroupCommand(
                groupId, userId
        ));
    }

}
