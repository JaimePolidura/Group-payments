package es.grouppayments.backend.payments.stripe._shared.application.onconnectedaccountcreated;

import es.grouppayments.backend.payments.stripe._shared.domain.StripeUser;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUsersService;
import es.grouppayments.backend.payments.stripe._shared.domain.events.StripeConnectedAccountCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripeConnectedAccountCreated {
    private final StripeUsersService stripeUsersService;

    @EventListener({StripeConnectedAccountCreated.class})
    public void on(StripeConnectedAccountCreated event){
        StripeUser stripeUser = stripeUsersService.getdByUserId(event.getUserId())
                .setConnectedAccountId(event.getConnectedAccountId());

        this.stripeUsersService.save(stripeUser);
    }
}
