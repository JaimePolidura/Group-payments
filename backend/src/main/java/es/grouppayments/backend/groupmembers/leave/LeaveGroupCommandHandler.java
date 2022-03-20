package es.grouppayments.backend.groupmembers.leave;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LeaveGroupCommandHandler implements CommandHandler<LeaveGroupCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;

    @Override
    public void handle(LeaveGroupCommand command) {
        Group groupToLeave = this.ensureGroupExistsAndGet(command.getGroupId());
        ensureGroupIsAbleToLeave(groupToLeave);
        GroupMember groupMember = this.ensureIsInGroup(groupToLeave, command.getUserId());

        if(groupMember.isAdmin()){
            this.groupService.deleteById(groupMember.getGroupId());
        }else{
            this.groupMemberService.deleteByUserId(command.getUserId());
        }
    }

    private void ensureGroupIsAbleToLeave(Group group){
        if(!group.canMembersJoinLeave())
            throw new IllegalState("You cannot leave the group right now");
    }

    private Group ensureGroupExistsAndGet(UUID groupId){
        return this.groupService.findByIdOrThrowException(groupId);
    }

    private GroupMember ensureIsInGroup(Group group, UUID userId){
        GroupMember groupMember = this.groupMemberService.findGroupMemberByUserIdOrThrowException(userId);

        if(!groupMember.getGroupId().equals(group.getGroupId())){
            throw new ResourceNotFound("You dont belong to that group");
        }else{
            return groupMember;
        }
    }

}
