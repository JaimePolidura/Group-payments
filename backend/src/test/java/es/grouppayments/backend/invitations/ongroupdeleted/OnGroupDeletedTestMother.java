package es.grouppayments.backend.invitations.ongroupdeleted;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;

public class OnGroupDeletedTestMother extends InvitationTestMother {
    private final OnGroupDeleted onGroupDeleted;

    public OnGroupDeletedTestMother(){
        this.onGroupDeleted = new OnGroupDeleted(
                new InvitationService(super.invitationsRepository(), super.testEventBus)
        );
    }

    public void on(GroupDeleted groupDeleted){
        this.onGroupDeleted.on(groupDeleted);
    }
}
