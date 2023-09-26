package es.grouppayments.backend.payments.userpaymentsinfo.changecard.confirm;

import es.grouppayments.backend._shared.application.ConfirmTokenService;
import es.grouppayments.backend._shared.application.ConfirmationAction;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo.changecard.ChangeCardTokenBody;
import es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink.GetConnectedAccountLinkQuery;
import es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink.GetConnectedAccountLinkQueryHandler;
import es.grouppayments.backend.payments.userpaymentsinfo.register.RegisterWithStripeCommand;
import es.grouppayments.backend.payments.userpaymentsinfo.register.RegisterWithStripeCommandHandler;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.IllegalType;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class ConfirmChangeCardCommandHandler implements CommandHandler<ConfirmChangeCardCommand> {
    private final ConfirmTokenService confirmTokenService;
    private final UsersService usersService;
    private final PaymentUsersInfoService paymentUsersInfoService;
    private final RegisterWithStripeCommandHandler stripeUserRegister;
    private final GetConnectedAccountLinkQueryHandler connectedAccountLinkCreator;

    @Override
    public void handle(ConfirmChangeCardCommand command) {
        String token = command.getToken();
        this.ensureTokenNotExpired(token);
        this.ensureCorrectAction(token);
        User user = this.ensureCorrectUser(token);
        String changeCardTokenBodyJSON = this.confirmTokenService.getExtra(token);
        ChangeCardTokenBody changeCardTokenBody = this.getChangeCardTokenBodyFromJSON(changeCardTokenBodyJSON);

        this.paymentUsersInfoService.deleteByUserId(user.getUserId());
        this.stripeUserRegister.handle(new RegisterWithStripeCommand(
                user.getUserId(), changeCardTokenBody.getPaymentMethod(), changeCardTokenBody.getDob()
        ));
    }

    private ChangeCardTokenBody getChangeCardTokenBodyFromJSON(String rawJSON) {
        return ChangeCardTokenBody.fromPrimitives((new JSONObject(rawJSON)).toMap());
    }

    private User ensureCorrectUser(String token){
        UUID userId = this.confirmTokenService.getUserId(token);

        return this.usersService.getByUserId(userId);
    }

    private void ensureCorrectAction(String token){
        ConfirmationAction action = this.confirmTokenService.getAction(token);

        if (action != ConfirmationAction.CHANGE_CARD)
            throw new IllegalType("Invalid token");
    }

    private void ensureTokenNotExpired(String token) {
        if(this.confirmTokenService.isExpired(token))
            throw new IllegalState("Token expired");
    }
}
