package es.grouppayments.backend.eventlog.onlogeableevent;

import es.grouppayments.backend.invitations.create.InvitationCreated;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public final class OnEventTest extends OnEventTestMother {
    @Test
    public void shouldSave(){
        UUID invitationId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();
        UUID fromUserId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        var eventto1 = new InvitationCreated(invitationId, toUserId, fromUserId, groupId, "hola", LocalDateTime.now());
        super.on(eventto1);

        assertEventLogExistsByUsername(toUserId);
    }

    @Test
    public void shouldMatchContent(){
        UUID invitationId = UUID.randomUUID();
        UUID toUserId = UUID.randomUUID();
        UUID fromUserId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        var evento = new InvitationCreated(invitationId, toUserId, fromUserId, groupId, "hola", LocalDateTime.now());
        super.on(evento);

        assertEventLogContent(toUserId, e -> e.getEventName().equalsIgnoreCase(evento.name()));
        assertEventLogContent(toUserId, e -> e.getUsersId().get(0).equals(toUserId));
        assertEventLogContent(toUserId, e -> e.getBody().equals(evento.body()));
    }
}
