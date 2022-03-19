package es.grouppayments.backend.payments.stripe.onpaymentmethodcreated;


import es.grouppayments.backend.payments.stripe._shared.domain.StripeUser;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUsersService;
import es.grouppayments.backend.payments.stripe._shared.domain.events.StripePaymentMethodCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripePaymentMethodCreated {
    private final StripeUsersService stripeUsersService;

    @EventListener({StripePaymentMethodCreated.class})
    public void on(StripePaymentMethodCreated event){
        StripeUser stripeUser = stripeUsersService.getdByUserId(event.getUserId())
                .setPaymentMethod(event.getPaymentMethodId());

        this.stripeUsersService.save(stripeUser);
    }
}
