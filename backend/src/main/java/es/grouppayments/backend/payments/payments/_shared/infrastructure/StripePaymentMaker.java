package es.grouppayments.backend.payments.payments._shared.infrastructure;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;
import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeServiceImpl;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class StripePaymentMaker implements PaymentMakerService {
    private final PaymentUsersInfoService stripeUsersService;
    private final StripeServiceImpl stripeService;
    private final CommissionPolicy commissionPolicy;

    @Override
    public String makePayment(UUID fromUserId, UUID toUserId, double money, Currency currencyCode) throws Exception {
        PaymentUserInfo fromConnectedAccount = this.stripeUsersService.getByUserId(fromUserId);
        PaymentUserInfo toConnectedAccount = this.stripeUsersService.getByUserId(toUserId);
        long totalFee = (long) this.commissionPolicy.collecteFee(money);

        PaymentIntent paymentIntent = PaymentIntent.create(
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (money * 100))
                        .addPaymentMethodType("card")
                        .setCurrency(currencyCode.name())
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
