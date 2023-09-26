package es.grouppayments.backend.users.users.oncreditcardregistered;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserState;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnCreditCardRegistered {
    private final UsersService usersService;

    @EventListener({StripeConnectedAccountCreated.class})
    public void on(StripeConnectedAccountCreated event){
        User user = this.usersService.getByUserId(event.getUserId())
                .withSignUpState(UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED);

        this.usersService.update(user);
    }
}
