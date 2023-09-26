package es.grouppayments.backend.users.users.delete.prepare;

import es.grouppayments.backend._shared.application.ConfirmationAction;
import es.grouppayments.backend._shared.domain.EmailSender;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend._shared.application.ConfirmTokenService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class PrepareDeleteAccountCommandHandler implements CommandHandler<PrepareDeleteAccountCommand> {
    public static final String DELETE_ACCOUNT_EMAIL_SUBJECT = "Delete account";

    @Value("${grouppayments.frontend.route}")
    private String frontendRoute;

    private final EmailSender emailSender;
    private final ConfirmTokenService deleteAccountJWTService;
    private final UsersService usersService;

    @Override
    public void handle(PrepareDeleteAccountCommand command) {
        User userToDelete = this.usersService.getByUserId(command.getUserId());
        String deleteUserToken = this.deleteAccountJWTService.generate(command.getUserId(), ConfirmationAction.DELETE_ACCOUNT);
        String userEmail = userToDelete.getEmail();

        this.emailSender.send(
                userEmail,
                DELETE_ACCOUNT_EMAIL_SUBJECT,
                String.format("Open this link to delete your account %s", this.createLinkToDeleteAccount(deleteUserToken))
        );
    }

    private String createLinkToDeleteAccount(String token){
        return String.format("%s%s?token=%s", this.frontendRoute, "/delete", token);
    }
}
