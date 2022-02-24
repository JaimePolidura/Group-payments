package es.grouppayments.backend.groups.delete;

import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DeleteGroupCommandHandler implements CommandHandler<DeleteGroupCommand> {
    private final GroupService groupService;

    @Override
    public void handle(DeleteGroupCommand deleteGroupCommand) {
        ensureAdminOfGroup(deleteGroupCommand.getGroupId(), deleteGroupCommand.getUserId());

        this.groupService.deleteById(deleteGroupCommand.getGroupId());
    }

    private void ensureAdminOfGroup(UUID groupId, UUID userId){
        boolean isAdmin = groupService.findByUsernameHost(userId)
                .orElseThrow(() -> new NotTheOwner("Your are not the admin of the gruop"))
                .getAdminUserId().equals(groupId);

        if(!isAdmin){
            throw new NotTheOwner("Your are not the admin of the gruop");
        }
    }
}
