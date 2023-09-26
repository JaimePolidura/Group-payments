package es.grouppayments.backend.invitations.onuserdeleted;

import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations.onuserdeletd.OnUserDeletedInvitation;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;

public class OnUserDeletedInvitationsTestMother extends InvitationTestMother {
    private final OnUserDeletedInvitation eventListener;

    public OnUserDeletedInvitationsTestMother(){
        this.eventListener = new OnUserDeletedInvitation(
                new InvitationService(super.invitationsRepository(), super.testEventBus)
        );
    }

    public void on(UserDeleted event){
        this.eventListener.on(event);
    }
}
