package es.grouppayments.backend.invitations.onpaymentinitialized;

import es.grouppayments.backend.invitations.InvitationTestMother;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.invitations.ongroupaymentinitialized.OnGroupPaymentInitalized;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupPaymentInitialized;

public class OnPaymentInitializedTestMother extends InvitationTestMother {
    private final OnGroupPaymentInitalized onGroupPaymentInitalized;

    public OnPaymentInitializedTestMother(){
        this.onGroupPaymentInitalized = new OnGroupPaymentInitalized(
                new InvitationService(super.invitationsRepository(), super.testEventBus)
        );
    }

    public void on(GroupPaymentInitialized groupPaymentInitialized){
        this.onGroupPaymentInitalized.on(groupPaymentInitialized);
    }
}
