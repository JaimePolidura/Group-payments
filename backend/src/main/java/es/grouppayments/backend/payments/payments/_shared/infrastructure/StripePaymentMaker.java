package es.grouppayments.backend.payments.payments._shared.infrastructure;

import com.stripe.model.PaymentIntent;
import com.stripe.model.Transfer;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.TransferCreateParams;
import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.StripeUsersService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class StripePaymentMaker implements PaymentMakerService {
    private final StripeUsersService stripeUsersService;
    private final StripeService stripeService;

    @Override
    public String paymentMemberToApp(UUID userId, double money, String currencyCode) throws Exception {
        StripeUser stripeUser = this.stripeUsersService.getdByUserId(userId);
        String consumerId = stripeUser.getCustomerId();
        String paymentMethodId = stripeUser.getPaymentMethod();

        return PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (money * 100))
                        .setCurrency(currencyCode.toLowerCase())
                        .addPaymentMethodType("card")
                        .setCustomer(consumerId)
                        .setPaymentMethod(paymentMethodId)
                        .setConfirm(true)
                        .setOffSession(false)
                        .build()
        ).toJson();
    }

    @Override
    public String paymentAppToAdmin(UUID userId, double money, String currencyCode) throws Exception {
        String stripeConnectedAccountId = this.stripeUsersService.getdByUserId(userId)
                .getConnectedAccountId();

        return Transfer.create(
                TransferCreateParams.builder()
                        .setAmount((long) (money * 100))
                        .setCurrency(currencyCode.toLowerCase())
                        .setDestination(stripeConnectedAccountId)
                        .setTransferGroup("order#1")
                        .build()
        ).toJson();
    }
}
