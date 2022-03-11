package es.grouppayments.backend.groups.edit;

import es.grouppayments.backend.groups.GroupTestMother;
import es.grouppayments.backend.groups._shared.domain.GroupService;

import java.util.UUID;

public class EditGroupTestMother extends GroupTestMother {
    private final EditGroupCommandHandler handler;

    public EditGroupTestMother(){
        this.handler = new EditGroupCommandHandler(
                new GroupService(super.groupRepository(), super.testEventBus)
        );
    }

    public void execute(UUID groupId, UUID userId, double newMoney, String newDescription){
        this.handler.handle(new EditGroupCommand(groupId, userId, newMoney, newDescription));
    }
}
