package es.grouppayments.backend.users.users.delete.confirm;

import es.grouppayments.backend._shared.application.ConfirmationAction;
import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.IllegalType;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.UUID;

public final class ConfirmUserDeleteTest extends ConfirmUserDeleteTestMother{
    @Test
    public void shouldDelete(){
        super.tokenService.getOtherWillReturn(ConfirmationAction.DELETE_ACCOUNT.name());
        UUID userId = UUID.randomUUID();
        addUser(userId);

        execute(new ConfirmDeleteAccountCommand(userId.toString()));

        assertUserDeleted(userId);
        assertEventRaised(UserDeleted.class);
    }

    @Test(expected = ResourceNotFound.class)
    public void userNotExits(){
        //Valid action
        super.tokenService.getOtherWillReturn(ConfirmationAction.DELETE_ACCOUNT.name());

        execute(new ConfirmDeleteAccountCommand(UUID.randomUUID().toString()));
    }
    
    @Test(expected = Exception.class)
    public void invalidAction(){
        super.tokenService.getOtherWillReturn("invalidactino");

        execute(new ConfirmDeleteAccountCommand("randomtoken"));
    }

    @Test(expected = IllegalState.class)
    public void tokenExpired(){
        super.tokenService.willBeExpired();

        execute(new ConfirmDeleteAccountCommand("randomtoken"));
    }
}
