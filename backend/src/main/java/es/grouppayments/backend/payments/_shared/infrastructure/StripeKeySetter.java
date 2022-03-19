package es.grouppayments.backend.payments._shared.infrastructure;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public final class StripeKeySetter implements CommandLineRunner {
    @Value("${stripe.key.private}") private String privateKey;

    @Override
    public void run(String... args) throws Exception {
        Stripe.apiKey = this.privateKey;
    }
}
