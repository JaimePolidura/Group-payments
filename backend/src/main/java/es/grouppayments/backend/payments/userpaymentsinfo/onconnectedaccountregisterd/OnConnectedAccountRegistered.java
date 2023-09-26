package es.grouppayments.backend.payments.userpaymentsinfo.onconnectedaccountregisterd;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnConnectedAccountRegistered {
    private final PaymentUsersInfoService stripeUsersService;

    @EventListener({StripeConnectedAccountRegistered.class})
    public void on(StripeConnectedAccountRegistered event){
        PaymentUserInfo stripeUser = stripeUsersService.getByUserId(event.getUserId());

        this.stripeUsersService.save(stripeUser.setAddedDataInStripeConnectedAccount());
    }
}
