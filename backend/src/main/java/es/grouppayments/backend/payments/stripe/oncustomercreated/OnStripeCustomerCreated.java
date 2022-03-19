package es.grouppayments.backend.payments.stripe.oncustomercreated;

import es.grouppayments.backend.payments.stripe._shared.domain.StripeUser;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUsersService;
import es.grouppayments.backend.payments.stripe._shared.domain.events.StripeCustomerCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripeCustomerCreated {
    private final StripeUsersService stripeUsersService;

    @EventListener({StripeCustomerCreated.class})
    public void on(StripeCustomerCreated event){
        StripeUser stripeUser = stripeUsersService.findByUserId(event.getUserId())
                .setCustomerId(event.getCustomerId());

        this.stripeUsersService.save(stripeUser);
    }
}
