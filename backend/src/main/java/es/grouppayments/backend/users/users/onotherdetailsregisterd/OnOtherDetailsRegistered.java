package es.grouppayments.backend.users.users.onotherdetailsregisterd;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserState;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnOtherDetailsRegistered {
    private final UsersService usersService;

    @EventListener({StripeConnectedAccountRegistered.class})
    public void on(StripeConnectedAccountRegistered event){
        User user = this.usersService.getByUserId(event.getUserId());

        this.usersService.update(user.withSignUpState(UserState.SIGNUP_ALL_COMPLETED));
    }
}
