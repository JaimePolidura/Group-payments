package es.grouppayments.backend.invitations.getbyuserid;

import es.grouppayments.backend.invitations._shared.domain.Invitation;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public final class GetInvitationsByUserIdTest extends GetInvitationsByUserIdTestMother{
    @Test
    public void shouldGet(){
        UUID userId = UUID.randomUUID();
        addInvitation(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), userId);
        addInvitation(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), userId);
        addInvitation(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), userId);
        addInvitation(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        List<Invitation> invitiations = execute(new GetInvitationsByUserIdQuery(userId))
                .getInvitiations();

        assertNotEmptyCollection(invitiations);
        assertCollectionSize(invitiations, 3);
        assertContentListMatches(invitiations, invitiation -> invitiation.getToUserId().equals(userId));
    }

    @Test
    public void shouldGetEmptyList(){
        assertEmptyCollection(execute(new GetInvitationsByUserIdQuery(UUID.randomUUID())).getInvitiations());
    }
}
