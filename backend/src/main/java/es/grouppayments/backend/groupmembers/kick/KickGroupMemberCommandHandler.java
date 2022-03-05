package es.grouppayments.backend.groupmembers.kick;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class KickGroupMemberCommandHandler implements CommandHandler<KickGroupMemberCommand> {
    private final GroupMemberService groupMemberService;
    private final GroupService groupService;

    @Override
    public void handle(KickGroupMemberCommand command) {
        Group group = ensureGroupExistsAndGet(command.getGroupId());
        ensureAdminOfGroup(group, command.getUserId());
        ensureUserToKickInGroup(command.getUserIdToKick(), group.getGroupId());

        groupMemberService.deleteByUserId(command.getUserIdToKick());
    }

    private Group ensureGroupExistsAndGet(UUID groupId){
        return this.groupService.findById(groupId)
                .orElseThrow(() -> new ResourceNotFound("Group doesnt exists"));
    }

    private void ensureAdminOfGroup(Group group, UUID adminUserId){
        if(!adminUserId.equals(group.getAdminUserId())){
            throw new NotTheOwner("You are not the owner of that group");
        }
    }

    private void ensureUserToKickInGroup(UUID userIdToKick, UUID groupId){
        GroupMember groupMember = this.groupMemberService.findGroupMemberByUserId(userIdToKick)
                .orElseThrow(() -> new ResourceNotFound("The user to that group"));

        if(!groupMember.getGroupId().equals(groupId)){
            throw new ResourceNotFound("The user to that group");
        }
    }
}
