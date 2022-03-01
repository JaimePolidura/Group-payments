package es.grouppayments.backend.groupmembers.join;

import _shared.TestMother;
import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class JoinGroupTestMother extends GroupMembersTestMother {
    protected JoinGroupCommandHandler joinGroupCommandHandler;

    public JoinGroupTestMother(){
        super();

        this.joinGroupCommandHandler = new JoinGroupCommandHandler(
                new GroupService(super.groupRepository, this.testEventBus),
                new GroupMemberService(super.groupMemberRepository, this.testEventBus),
                super.testEventBus
        );
    }

    protected void executeJoinGroupCommandHandler(UUID idOfGroupToJoin){
        this.joinGroupCommandHandler.handle(
                new JoinGroupCommand(UUID.randomUUID(), idOfGroupToJoin)
        );
    }

    protected void executeJoinGroupCommandHandler(UUID idOfGroupToJoin, UUID userId){
        this.joinGroupCommandHandler.handle(
                new JoinGroupCommand(userId, idOfGroupToJoin)
        );
    }
}
