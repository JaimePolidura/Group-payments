package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class CreateGroupTestMother extends GroupTestMother {
    protected CreateGroupCommandHandler createGroupCommandHandler;

    public CreateGroupTestMother(){
        this.createGroupCommandHandler = new CreateGroupCommandHandler(
                new GroupService(groupRepository(), this.testEventBus),
                new GroupMemberService(groupMemberRepository(), this.testEventBus)
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, UUID.randomUUID(), 100, "group")
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate, UUID userId){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, userId, 100, "group")
        );
    }
}
