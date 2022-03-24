package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountregisterd;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.StripeUsersService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnConnectedAccountRegistered {
    private final StripeUsersService stripeUsersService;

    @EventListener({StripeConnectedAccountRegistered.class})
    public void on(StripeConnectedAccountRegistered event){
        StripeUser stripeUser = stripeUsersService.getdByUserId(event.getUserId());

        this.stripeUsersService.save(stripeUser.setAddedDataInStripeConnectedAccount());
    }
}
