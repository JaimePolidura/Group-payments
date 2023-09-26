package es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure;

import com.stripe.model.*;
import com.stripe.param.*;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountCreated;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeCustomerCreated;
import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class StripeServiceImpl implements StripeService{
    private final EventBus eventBus;
    private final UsersService usersService;
    private final PaymentUsersInfoService stripeUsersService;

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

        this.eventBus.publish(new StripeCustomerCreated(userId, customer.getId(), paymentMethodId));
    }

    @SneakyThrows
    public Account createConnectedAccount(UUID userId, Dob dob){
        User user = this.usersService.getByUserId(userId);

        String countryCode = user.getCountry();
        Currency currencyCode = this.usersService.getByUserId(userId).getCurrency();

        String[] nameSplitted = user.getUsername().split(" ");
        String firstName = nameSplitted[0];
        String lastName = nameSplitted.length == 1 ? firstName : nameSplitted[nameSplitted.length - 1];

        Account account = Account.create(
                AccountCreateParams.builder()
                        .setCountry(countryCode)
                        .setEmail(user.getEmail())
                        .setIndividual(AccountCreateParams.Individual.builder()
                                .setFirstName(user.getUsername())
                                .setLastName(lastName)
                                .setDob(AccountCreateParams.Individual.Dob.builder()
                                        .setYear(dob.getYear())
                                        .setDay(dob.getDay())
                                        .setMonth(dob.getMonth())
                                        .build())
                                .setEmail(user.getEmail())
                                .build())
                        .setDefaultCurrency(currencyCode.name())
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

        this.eventBus.publish(new StripeConnectedAccountCreated(userId, account.getId()));

        return account;
    }

    @SneakyThrows
    public boolean hasRegisteredInConnectedAccount(UUID userId){
        String connectedAccountId = this.stripeUsersService.getByUserId(userId)
                .getConnectedAccountId();

        return Account.retrieve(connectedAccountId).getDetailsSubmitted();
    }

    @SneakyThrows
    public String createConnectedAccountLink(String connectedAccountId){
        return AccountLink.create(
                AccountLinkCreateParams.builder()
                        .setCollect(AccountLinkCreateParams.Collect.EVENTUALLY_DUE)
                        .setAccount(connectedAccountId)
                        .setRefreshUrl("http://localhost:4200/register")
                        .setReturnUrl("http://localhost:4200/main")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build()
        ).getUrl();
    }
}
