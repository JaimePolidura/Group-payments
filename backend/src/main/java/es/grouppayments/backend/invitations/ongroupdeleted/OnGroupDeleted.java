package es.grouppayments.backend.invitations.ongroupdeleted;

import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.grouppayments.backend.invitations._shared.application.InvitationService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("ongroupdeleted-invitation")
@AllArgsConstructor
public final class OnGroupDeleted {
    private final InvitationService invitationService;

    @EventListener
    public void on(GroupDeleted event){
        this.invitationService.deleteByGroupId(event.getGroupId());
    }
}
