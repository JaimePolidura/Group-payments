package es.grouppayments.backend.gruops.create;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups.create.CreateGroupCommand;
import es.grouppayments.backend.groups.create.CreateGroupCommandHandler;

import java.util.UUID;


public class CreateGroupTestMother extends TestMother {
    protected CreateGroupCommandHandler createGroupCommandHandler;

    public CreateGroupTestMother(){
        super();

        this.createGroupCommandHandler = new CreateGroupCommandHandler(
                new GroupService(super.groupRepository, this.testEventBus),
                new GroupMemberService(super.groupMemberRepository, this.testEventBus)
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
