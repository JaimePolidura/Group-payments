package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CreateGroupCommandHandler implements CommandHandler<CreateGroupCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMembersService;

    @Override
    public void handle(CreateGroupCommand createGroupCommand) {
        ensureNotAlreadyInGroupOrLeaveGroup(createGroupCommand.getUserId());

        this.groupService.create(
                createGroupCommand.getGroupId(),
                createGroupCommand.getTitle(),
                createGroupCommand.getMoney(),
                createGroupCommand.getUserId()
        );
    }

    private void ensureNotAlreadyInGroupOrLeaveGroup(UUID userId){
        groupService.deleteGroupIfIsAdmin(userId);
        groupMembersService.leaveGroupIfMember(userId);
    }
}
