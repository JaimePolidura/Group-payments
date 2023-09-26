package _shared.payments.paymentsuserinfo;

import com.stripe.model.PaymentIntent;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfoRepository;

import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsingPaymentUserInfo {
    String DEFAULT_PAYMENT_METHOD = "pm";
    String DEFAULT_CUSTOMER_ID = "cm";
    String DEFAULT_CONNECTED_ACCOUNT_ID = "ca";

    PaymentUserInfoRepository paymentUserInfoRepository();

    default void addPaymentInfo(UUID userId){
        addPaymentInfo(userId,true);
    }

    default void addPaymentInfo(UUID userId, boolean registeredWithStripeCa){
        this.paymentUserInfoRepository().save(new PaymentUserInfo(userId, DEFAULT_PAYMENT_METHOD, DEFAULT_CUSTOMER_ID, DEFAULT_CONNECTED_ACCOUNT_ID, registeredWithStripeCa));
    }

    default void assertUserPaymentInfoDeleted(UUID userId){
        assertTrue(this.paymentUserInfoRepository().findByUserId(userId).isEmpty());
    }

    default void assertUserPaymentInfoCreated(UUID userId){
        assertTrue(this.paymentUserInfoRepository().findByUserId(userId).isPresent());
    }

    default void assertUserPaymentInfoContent(UUID userId, Predicate<PaymentUserInfo> condition){
        assertTrue(condition.test(this.paymentUserInfoRepository().findByUserId(userId).get()));
    }
}
