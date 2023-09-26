package es.grouppayments.backend.invitations.ongroupaymentinitialized;

import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.payments.payments._shared.domain.events.grouppayment.GroupPaymentInitialized;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("onpaymentinitialized-invitation")
@AllArgsConstructor
public final class OnGroupPaymentInitalized {
    private final InvitationService invitationService;

    @EventListener
    public void on(GroupPaymentInitialized event){
        this.invitationService.deleteByGroupId(event.getGroupId());
    }
}
