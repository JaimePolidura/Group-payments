package es.grouppayments.backend.invitations.reject;

import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations._shared.domain.Invitation;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class RejectInvitationCommandHandler implements CommandHandler<RejectInvitationCommand> {
    private final InvitationService invitationService;
    private final EventBus eventBus;

    @Override
    public void handle(RejectInvitationCommand command) {
        Invitation invitation = this.ensureInvitationExists(command);
        this.ensureUserIsTheInvitated(invitation, command);

        this.invitationService.deleteByInvitationId(command.getInvitationId());

        this.eventBus.publish(new InvitationRejected(command.getInvitationId(), invitation.getToUserId(),
                invitation.getFromUserId(), invitation.getGroupId()));
    }

    private Invitation ensureInvitationExists(RejectInvitationCommand command){
        return this.invitationService.getByInvitationOId(command.getInvitationId());
    }

    private void ensureUserIsTheInvitated(Invitation invitation, RejectInvitationCommand command){
        if(!invitation.getToUserId().equals(command.getUserId()))
            throw new NotTheOwner("You are not in that invitation");
    }
}
