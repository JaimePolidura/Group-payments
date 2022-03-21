package es.grouppayments.backend.payments.userpaymentsinfo._shared.application.oncustomercreated;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUsersService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripeCustomerCreated {
    private final StripeUsersService stripeUsersService;

    @EventListener({StripeCustomerCreated.class})
    public void on(StripeCustomerCreated event){
        this.stripeUsersService.save(StripeUser.create(event.getUserId(), event.getCustomerId()));
    }
}
