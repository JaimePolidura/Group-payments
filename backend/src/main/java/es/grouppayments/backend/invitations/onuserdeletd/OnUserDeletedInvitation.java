package es.grouppayments.backend.invitations.onuserdeletd;

import es.grouppayments.backend.invitations._shared.application.InvitationService;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnUserDeletedInvitation {
    private final InvitationService invitationService;

    @EventListener({UserDeleted.class})
    public void on(UserDeleted event){
        this.invitationService.deleteByUserId(event.getUserId());
    }
}
