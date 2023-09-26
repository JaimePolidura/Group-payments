package es.grouppayments.backend.invitations.accept;

import es.grouppayments.backend.groupmembers.join.JoinGroupCommand;
import es.grouppayments.backend.groupmembers.join.JoinGroupCommandHandler;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations._shared.domain.Invitation;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class AcceptInvitationCommandHandler implements CommandHandler<AcceptInvitationCommand> {
    private final InvitationService invitationService;
    private final JoinGroupCommandHandler joinGroupCommandHandler;
    private final EventBus eventBus;

    @Override
    public void handle(AcceptInvitationCommand command) {
        Invitation invitiation = this.ensureInvitationsExists(command);
        this.ensureUserBelongsToInvitation(command, invitiation);

        this.joinGroupCommandHandler.handle(new JoinGroupCommand(
                command.getUserId(),
                invitiation.getGroupId()
        ));

        this.invitationService.deleteByInvitationId(command.getInvitationId());

        this.eventBus.publish(new InvitationAccepted(invitiation.getGroupId(), invitiation.getFromUserId(),
                invitiation.getToUserId()));
    }

    private Invitation ensureInvitationsExists(AcceptInvitationCommand command){
        return this.invitationService.getByInvitationOId(command.getInvitationId());
    }

    private void ensureUserBelongsToInvitation(AcceptInvitationCommand command, Invitation invitiation){
        if(!command.getUserId().equals(invitiation.getToUserId())){
            throw new NotTheOwner("No one has invited you");
        }
    }
}
