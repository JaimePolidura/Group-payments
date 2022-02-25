package es.grouppayments.backend.groups.delete;

import es.grouppayments.backend.TestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class DeleteGroupTestMother extends TestMother {
    private final DeleteGroupCommandHandler deleteGroupCommandHandler;

    public DeleteGroupTestMother(){
        this.deleteGroupCommandHandler = new DeleteGroupCommandHandler(
                new GroupService(super.groupRepository, super.testEventBus)
        );
    }

    protected void executeDeleteGroup(UUID groupId, UUID userId){
        this.deleteGroupCommandHandler.handle(new DeleteGroupCommand(userId, groupId));
    }
}
