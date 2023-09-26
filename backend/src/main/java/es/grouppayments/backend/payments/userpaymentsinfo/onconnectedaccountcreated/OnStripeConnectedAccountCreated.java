package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountcreated;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnStripeConnectedAccountCreated {
    private final PaymentUsersInfoService stripeUsersService;

    @EventListener({StripeConnectedAccountCreated.class})
    public void on(StripeConnectedAccountCreated event){
        PaymentUserInfo stripeUser = stripeUsersService.getByUserId(event.getUserId())
                .setConnectedAccountId(event.getConnectedAccountId());

        this.stripeUsersService.save(stripeUser);
    }
}
