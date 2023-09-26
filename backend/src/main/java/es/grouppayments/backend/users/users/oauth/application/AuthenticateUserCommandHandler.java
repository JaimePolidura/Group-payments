package es.grouppayments.backend.users.users.oauth.application;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.events.StripeConnectedAccountRegistered;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeService;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserState;
import es.grouppayments.backend.users.users.oauth.domain.AuthenticateUserCommand;
import es.grouppayments.backend.users.users.oauth.domain.NewUserRegistered;
import es.grouppayments.backend.users.usersimage.SaveUserImageService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public final class AuthenticateUserCommandHandler implements CommandHandler<AuthenticateUserCommand> {
    private final PaymentUsersInfoService paymentUserInfo;
    private final SaveUserImageService saveUserImageUseCase;
    private final StripeService stripeService;
    private final UsersService usersService;
    private final EventBus eventBus;

    @Override
    public void handle(AuthenticateUserCommand command) {
        User authenticatedUser = createNewUserIfNotExistsAndGetUserId(command);

        this.checkIfRegisteredInStripeConnectedAccount(authenticatedUser);
    }

    private User createNewUserIfNotExistsAndGetUserId(AuthenticateUserCommand command) {
        if(usersService.findByEmail(command.getEmail()).isEmpty()){
            return registerNewUser(command);
        } else{
            this.eventBus.publish(new NewUserRegistered(usersService.findByEmail(command.getEmail()).get()));

            return usersService.findByEmail(command.getEmail()).get();
        }
    }

    private User registerNewUser(AuthenticateUserCommand command) {
        int imageUserId = this.saveUserImageUseCase.save(command.getPhotoUrl());

        User newUser = usersService.create(command.getUsername(), command.getEmail(), imageUserId,
                command.getCountryCode().toUpperCase());

        this.eventBus.publish(new NewUserRegistered(newUser));

        return newUser;
    }

    private User checkIfRegisteredInStripeConnectedAccount(User user){
        if(user.getState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED){
            PaymentUserInfo userPaymentInfo = this.paymentUserInfo.getByUserId(user.getUserId());

            //Hasnt registered in stripe
            if(!userPaymentInfo.isAddedDataInStripeConnectedAccount()){
                boolean hasRegistedConnectedAccount = this.stripeService.hasRegisteredInConnectedAccount(user.getUserId());

                if(hasRegistedConnectedAccount){
                    this.eventBus.publish(new StripeConnectedAccountRegistered(user.getUserId()));

                    return user.withSignUpState(UserState.SIGNUP_ALL_COMPLETED);
                }
            }
        }

        return user;
    }
}
