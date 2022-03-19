package es.grouppayments.backend.payments.stripe._shared.infrastructure;

import com.stripe.model.Customer;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SetupIntentCreateParams;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class StripeService {
    private final EventBus eventBus;

    @SneakyThrows
    public String setupIntent()  {
        SetupIntent setupIntent = SetupIntent.create(SetupIntentCreateParams.builder()
                .setUsage(SetupIntentCreateParams.Usage.ON_SESSION)
                .build()
        );

        eventBus.publish(new StripeS);

        return setupIntent.toJson();
    }

    @SneakyThrows
    public void createCustomer(String paymentMethodId){
        Customer.create(CustomerCreateParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build()
        );
    }


}
