package es.grouppayments.backend.groups.edit;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.IllegalLength;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class EditGroupCommandHandler implements CommandHandler<EditGroupCommand> {
    private final GroupService groupService;

    @Override
    public void handle(EditGroupCommand command) {
        this.ensureCorrectNewDescription(command.getNewDescription());
        this.ensureCorrectNewMoney(command.getNewMoney());
        Group groupToEdit = this.ensureGroupExists(command.getGroupId());
        this.ensureAdminOfGroup(groupToEdit, command.getUserId());

        Group groupUpdated = groupToEdit.changeMoney(command.getNewMoney())
                .changeDescription(command.getNewDescription());

        this.groupService.update(groupUpdated);
    }

    private Group ensureGroupExists(UUID groupId){
        return this.groupService.findById(groupId)
                .orElseThrow(() -> new ResourceNotFound("Group not exists"));
    }

    private void ensureAdminOfGroup(Group group, UUID userId){
        if(!group.getAdminUserId().equals(userId))
            throw new NotTheOwner("You dont belong to the group or you are not the admin");
    }

    private void ensureCorrectNewDescription(String newDecription){
        if(newDecription == null || newDecription.equals("") || newDecription.length() > 16){
            throw new IllegalLength("Max lenght for description is 16");
        }
    }

    private void ensureCorrectNewMoney(double newMoney){
        if(newMoney <= 0 || newMoney > 10000){
            throw new IllegalQuantity("Money limits (0, 10000)}");
        }
    }
}
