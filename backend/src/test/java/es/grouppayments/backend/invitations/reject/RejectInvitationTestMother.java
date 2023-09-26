package es.grouppayments.backend.invitations.reject;

import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;

public class RejectInvitationTestMother extends InvitationTestMother {
    private final RejectInvitationCommandHandler handler;

    public RejectInvitationTestMother(){
        this.handler = new RejectInvitationCommandHandler(
                new InvitationService(super.invitationsRepository(), super.testEventBus),
                super.testEventBus
        );
    }

    public void execute(RejectInvitationCommand command){
        this.handler.handle(command);
    }
}
