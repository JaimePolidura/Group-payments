package es.grouppayments.backend.groupmembers.join;

import es.grouppayments.backend._shared.domain.Utils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static es.grouppayments.backend._shared.domain.Utils.*;

@Service
@AllArgsConstructor
public final class JoinGroupCommandHandler implements CommandHandler<JoinGroupCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final EventBus eventBus;

    @Override
    public void handle(JoinGroupCommand command) {
        ensureGroupExists(command.getGroupId());
        ensureNotAlreadyInGroupOrLeaveGroup(command.getUserId());
        ensureGroupStateAvilableToJoin(command.getGroupId());

        this.groupMemberService.save(new GroupMember(
                command.getUserId(),
                command.getGroupId(),
                GroupMemberRole.USER
        ));

        this.eventBus.publish(new GroupMemberJoined(command.getUserId(), command.getGroupId()));
    }

    private void ensureGroupStateAvilableToJoin(UUID groupId){
        var canJoin = this.groupService.findByIdOrThrowException(groupId).canMembersJoinLeave();

        if(!canJoin)
            throw new IllegalState("Cannot join to group");
    }

    private void ensureGroupExists(UUID groupId){
        groupService.findByIdOrThrowException(groupId);
    }

    private void ensureNotAlreadyInGroupOrLeaveGroup(UUID userId){
        groupService.deleteGroupIfIsAdmin(userId);
        groupMemberService.leaveGroupIfMember(userId);
    }
}
