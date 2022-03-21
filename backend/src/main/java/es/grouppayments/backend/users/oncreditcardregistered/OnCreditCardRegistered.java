package es.grouppayments.backend.users.oncreditcardregistered;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnCreditCardRegistered {
    private final UsersService usersService;

    @EventListener({StripeConnectedAccountCreated.class})
    public void on(StripeConnectedAccountCreated event){
        User user = this.usersService.findByUserId(event.getUserId())
                .get()
                .updateSignUpState(UserState.SIGNUP_ALL_COMPLETED);

        this.usersService.update(user);
    }
}
