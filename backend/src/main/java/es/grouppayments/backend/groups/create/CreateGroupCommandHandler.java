package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        deleteGroupIfIsAdmin(userId);
        leaveGroupIfMember(userId);
    }

    private void deleteGroupIfIsAdmin(UUID userId){
        Optional<Group> groupAdminOptional = this.groupService.findByUsernameHost(userId);
        boolean isAdminOfGroup = groupAdminOptional.isPresent();

        if(isAdminOfGroup){
            this.groupService.deleteById(groupAdminOptional.get().getGroupId());
        }
    }

    private void leaveGroupIfMember(UUID userId){
         Optional<UUID> groupMemberOptional = groupMembersService.findGroupIdByUserId(userId);
         boolean isMemberOfGroup = groupMemberOptional.isPresent();

        if(isMemberOfGroup){
            this.groupMembersService.deleteByUserId(userId);
        }
    }
}
