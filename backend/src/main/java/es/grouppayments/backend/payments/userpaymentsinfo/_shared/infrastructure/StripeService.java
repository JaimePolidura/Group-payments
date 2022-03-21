package es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure;

import com.stripe.model.*;
import com.stripe.param.*;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class StripeService {
    private final EventBus eventBus;
    private final UsersService usersService;

    @SneakyThrows
    public String setupIntent()  {
        SetupIntent setupIntent = SetupIntent.create(SetupIntentCreateParams.builder()
                .setUsage(SetupIntentCreateParams.Usage.ON_SESSION)
                .build()
        );

        return setupIntent.toJson();
    }

    @SneakyThrows
    public void createCustomer(UUID userId, String paymentMethodId){
        Customer customer = Customer.create(CustomerCreateParams.builder()
                .setPaymentMethod(paymentMethodId)
                .build()
        );

        this.eventBus.publish(new StripeCustomerCreated(userId, paymentMethodId));
    }

    @SneakyThrows
    public Account createConnectedAccount(UUID userId){
        User user = this.usersService.findByUserId(userId).get();

        return Account.create(
                AccountCreateParams.builder()
                        .setCountry("ES")
                        .setEmail(user.getEmail())
                        .setIndividual(AccountCreateParams.Individual.builder()
                                .setFirstName(user.getUsername())
                                .setEmail(user.getEmail())
                                .build())
                        .setDefaultCurrency("eur")
                        .setBusinessType(AccountCreateParams.BusinessType.INDIVIDUAL)
                        .setCapabilities(AccountCreateParams.Capabilities.builder()
                                .setTransfers(AccountCreateParams.Capabilities.Transfers.builder()
                                        .setRequested(true)
                                        .build())
                                .build())
                        .setTosAcceptance(
                                AccountCreateParams.TosAcceptance
                                        .builder()
                                        .setServiceAgreement("recipient")
                                        .build()
                        )
                        .setType(AccountCreateParams.Type.EXPRESS)
                        .build()
        );
    }

    @SneakyThrows
    public String createConnectedAccountLink(UUID userId, String connectedAccountId){
        String url = AccountLink.create(
                AccountLinkCreateParams
                        .builder()
                        .setCollect(AccountLinkCreateParams.Collect.EVENTUALLY_DUE)
                        .setAccount(connectedAccountId)
                        .setRefreshUrl("http://localhost:4200/register")
                        .setReturnUrl("http://localhost:4200/main")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build()
        ).getUrl();

        this.eventBus.publish(new StripeConnectedAccountCreated(userId, connectedAccountId));

        return url;
    }
}
