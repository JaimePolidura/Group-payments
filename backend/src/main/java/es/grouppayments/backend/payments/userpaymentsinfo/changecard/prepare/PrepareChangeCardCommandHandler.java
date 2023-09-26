package es.grouppayments.backend.payments.userpaymentsinfo.changecard.prepare;

import es.grouppayments.backend._shared.application.ConfirmationAction;
import es.grouppayments.backend._shared.domain.EmailSender;
import es.grouppayments.backend.payments.userpaymentsinfo.changecard.ChangeCardTokenBody;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend._shared.application.ConfirmTokenService;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public final class PrepareChangeCardCommandHandler implements CommandHandler<PrepareChangeCardCommand> {
    public static final String DELETE_ACCOUNT_EMAIL_SUBJECT = "Change card";

    @Value("${grouppayments.frontend.route}")
    private String frontendRoute;

    private final EmailSender emailSender;
    private final ConfirmTokenService confirmationTokenService;
    private final UsersService usersService;

    @Override
    public void handle(PrepareChangeCardCommand command) {
        User userToDelete = this.usersService.getByUserId(command.getUserId());
        String userEmail = userToDelete.getEmail();

        ChangeCardTokenBody extraBody = new ChangeCardTokenBody(command.getPaymentMethod(), command.getDob());

        String deleteUserToken = this.confirmationTokenService.generate(
                command.getUserId(),
                ConfirmationAction.CHANGE_CARD,
                new JSONObject(extraBody.toPrimitives()).toString()
        );

        this.emailSender.send(
                userEmail,
                DELETE_ACCOUNT_EMAIL_SUBJECT,
                String.format("Open this link to delete your account %s", this.createLinkToDeleteAccount(deleteUserToken))
        );
    }

    private String createLinkToDeleteAccount(String token){
        return String.format("%s%s?token=%s", this.frontendRoute, "/changecard", token);
    }
}
