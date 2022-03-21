package es.grouppayments.backend.payments._shared.infrastructure;

import com.stripe.model.PaymentIntent;
import com.stripe.model.Transfer;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.TransferCreateParams;
import es.grouppayments.backend.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUser;
import es.grouppayments.backend.payments.stripe._shared.domain.StripeUsersService;
import es.grouppayments.backend.payments.stripe._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class StripePaymentMaker implements PaymentMakerService {
    private final StripeUsersService stripeUsersService;

    private final StripeService stripeService;

    @Override
    public String paymentMemberToApp(UUID userId, double money) throws Exception {
        StripeUser stripeUser = this.stripeUsersService.getdByUserId(userId);
        String consumerId = stripeUser.getCustomerId();
        String paymentMethodId = stripeUser.getPaymentMethod();

        return PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (money * 100))
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .setCustomer(consumerId)
                        .setPaymentMethod(paymentMethodId)
                        .setConfirm(true)
                        .setOffSession(false)
                        .build()
        ).toJson();
    }

    @Override
    public String paymentAppToAdmin(UUID userId, double money) throws Exception {
        String stripeConnectedAccountId = this.stripeUsersService.getdByUserId(userId)
                .getConnectedAccountId();

        return Transfer.create(
                TransferCreateParams
                        .builder()
                        .setAmount(4000L)
                        .setCurrency("eur")
                        .setDestination(stripeConnectedAccountId)
                        .setTransferGroup("order#1")
                        .build()
        ).toJson();
    }
}
