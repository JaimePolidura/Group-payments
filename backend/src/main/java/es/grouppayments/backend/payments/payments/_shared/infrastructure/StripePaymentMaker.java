package es.grouppayments.backend.payments.payments._shared.infrastructure;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;
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
    private final CommissionPolicy commissionPolicy;

    @Override
    public String makePayment(UUID fromUserId, UUID toUserId, double money, String currencyCode) throws Exception {
        StripeUser fromConnectedAccount = this.stripeUsersService.getdByUserId(fromUserId);
        StripeUser toConnectedAccount = this.stripeUsersService.getdByUserId(toUserId);
        long totalFee = (long) this.commissionPolicy.collecteFee(money);

        PaymentIntent paymentIntent = PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (money * 100))
                        .addPaymentMethodType("card")
                        .setCurrency(currencyCode)
                        .setApplicationFeeAmount(totalFee)
                        .setCustomer(fromConnectedAccount.getCustomerId())
                        .setPaymentMethod(fromConnectedAccount.getPaymentMethod())
                        .setTransferData(PaymentIntentCreateParams.TransferData.builder()
                                .setDestination(toConnectedAccount.getConnectedAccountId())
                                .build())
                        .build());

        paymentIntent.confirm();

        return paymentIntent.toJson();
    }
}
