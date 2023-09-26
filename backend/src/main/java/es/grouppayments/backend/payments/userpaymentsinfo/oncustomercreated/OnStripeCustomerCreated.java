package es.grouppayments.backend.payments.userpaymentsinfo.oncustomercreated;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripeCustomerCreated {
    private final PaymentUsersInfoService stripeUsersService;

    @EventListener({StripeCustomerCreated.class})
    public void on(StripeCustomerCreated event){
        this.stripeUsersService.save(PaymentUserInfo.create(event.getUserId(), event.getCustomerId(), event.getPaymentMethodId()));
    }
}
