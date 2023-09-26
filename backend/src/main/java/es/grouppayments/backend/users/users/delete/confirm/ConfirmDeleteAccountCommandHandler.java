package es.grouppayments.backend.users.users.delete.confirm;

import es.grouppayments.backend._shared.application.ConfirmationAction;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend._shared.application.ConfirmTokenService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.IllegalType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class ConfirmDeleteAccountCommandHandler implements CommandHandler<ConfirmDeleteAccountCommand> {
    private final ConfirmTokenService confirmTokenService;
    private final UsersService usersService;

    @Override
    public void handle(ConfirmDeleteAccountCommand command) {
        String token = command.getToken();
        this.ensureTokenNotExpired(token);
        this.ensureCorrectAction(token);
        User userToDelete = this.ensureCorrectUser(token);

        this.usersService.deleteByUserId(userToDelete.getUserId());
    }

    private void ensureTokenNotExpired(String token){
        if(this.confirmTokenService.isExpired(token))
            throw new IllegalState("Token expired");
    }

    private void ensureCorrectAction(String token){
        ConfirmationAction action = this.confirmTokenService.getAction(token);

        if (action != ConfirmationAction.DELETE_ACCOUNT)
            throw new IllegalType("Invalid token");
    }

    private User ensureCorrectUser(String token){
        UUID userId = this.confirmTokenService.getUserId(token);

        return this.usersService.getByUserId(userId);
    }
}
