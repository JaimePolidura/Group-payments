package es.grouppayments.backend.invitations.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.AlreadyExists;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class CreateInvitationCommandHandler implements CommandHandler<CreateInvitationCommand> {
    private final UsersService usersService;
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final InvitationService invitationService;

    @Override
    public void handle(CreateInvitationCommand command) {
        Group groupToInvite = this.ensureGroupExists(command);
        this.ensureCorrectStateOfGroup(groupToInvite);
        this.ensureUserToExists(command);
        this.ensureUserFromBelongsToTheGroup(command);
        this.ensureUserToDoesntBelongToAnyGroup(command);
        this.ensureAlreadyNotInvited(command);

        this.invitationService.save(command.getInvitationId(), command.getGroupId(), command.getToUserId(),
                command.getFromUserId(), groupToInvite.getDescription());
    }

    private void ensureCorrectStateOfGroup(Group group) {
        if(!group.canMembersJoinLeave())
            throw new IllegalState("You cannot invite users in the group");
    }

    private Group ensureGroupExists(CreateInvitationCommand command) {
        return this.groupService.findByIdOrThrowException(command.getGroupId());
    }

    private void ensureUserToExists(CreateInvitationCommand command){
        this.usersService.getByUserId(command.getToUserId());
    }

    private void ensureUserFromBelongsToTheGroup(CreateInvitationCommand command) {
        var belongsToGruop = this.groupMemberService.findGroupMemberByUserIdOrThrowException(command.getFromUserId())
                .getGroupId().equals(command.getGroupId());

        if(!belongsToGruop)
            throw new NotTheOwner("You dont belong to that gruop");
    }

    private void ensureUserToDoesntBelongToAnyGroup(CreateInvitationCommand command) {
        var isInGroup = this.groupMemberService.findGroupMember(command.getToUserId())
                .isPresent();

        if(isInGroup)
            throw new AlreadyExists("The user already belongs to a group");
    }

    private void ensureAlreadyNotInvited(CreateInvitationCommand command){
        if(this.invitationService.alreadyInvitedFromGroup(command.getToUserId(), command.getGroupId()))
            throw new AlreadyExists("Your group have already invited the user");
    }
}
