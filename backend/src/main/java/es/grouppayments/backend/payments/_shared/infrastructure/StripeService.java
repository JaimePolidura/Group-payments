package es.grouppayments.backend.payments._shared.infrastructure;

import com.stripe.model.Customer;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SetupIntentCreateParams;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public final class StripeService {
    @SneakyThrows
    public String setupIntent()  {
        return SetupIntent.create(SetupIntentCreateParams.builder()
                .setUsage(SetupIntentCreateParams.Usage.ON_SESSION)
                .build()
        ).toJson();
    }

    @SneakyThrows
    public void createCustomer(String paymentMethodId){
        Customer.create(CustomerCreateParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build()
        );
    }


}
