package es.grouppayments.backend.groupmembers.kick;

import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class KickGroupMemberTestMother extends GroupMembersTestMother {
    private final KickGroupMemberCommandHandler handler;

    public KickGroupMemberTestMother() {
        this.handler = new KickGroupMemberCommandHandler(
                new GroupMemberService(groupMemberRepository(), super.testEventBus),
                new GroupService(groupRepository(), super.testEventBus)
        );
    }

    protected void execute(UUID userId, UUID groupId, UUID userIdToKick){
        this.handler.handle(new KickGroupMemberCommand(userId, groupId, userIdToKick));
    }
}
