package es.grouppayments.backend.groups.create;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CreateGroupCommandHandler implements CommandHandler<CreateGroupCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMembersService;
    private final UsersService usersService;
    private final InvitationService invitationService;

    @Override
    public void handle(CreateGroupCommand command) {
        ensureNotAlreadyInGroupOrLeaveGroup(command.getUserId());
        List<User> usersToInvite = ensureUsersEmailToInviteExists(command.getUsersEmailToInvite());

        Currency currency = this.usersService.getByUserId(command.getUserId())
                .getCurrency();

        this.groupService.create(
                command.getGroupId(),
                command.getTitle(),
                command.getMoney(),
                command.getUserId(),
                currency
        );

        usersToInvite.forEach(userToInvite -> {
            this.invitationService.save(UUID.randomUUID(), command.getGroupId(), userToInvite.getUserId(),
                    command.getUserId(), command.getTitle());
        });
    }

    private List<User> ensureUsersEmailToInviteExists(List<String> usersEmailToInvite){
        if(usersEmailToInvite == null || usersEmailToInvite.size() == 0) return new LinkedList<>();

        List<User> users = new LinkedList<>();

        for (String userEmail : usersEmailToInvite) {
            this.usersService.findByEmail(userEmail).ifPresent(users::add);
        }

        return users;
    }

    private void ensureNotAlreadyInGroupOrLeaveGroup(UUID userId){
        groupService.deleteGroupIfIsAdmin(userId);
        groupMembersService.leaveGroupIfMember(userId);
    }
}
