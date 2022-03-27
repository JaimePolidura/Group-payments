package es.grouppayments.backend.payments.payments.transfer;

import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.CannotBeYourself;
import es.jaime.javaddd.domain.exceptions.IllegalLength;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public final class TransferCommandHandler implements CommandHandler<TransferCommand> {
    private final UsersService usersService;
    private final PaymentMakerService paymentMakerService;
    private final CurrencyService currencyService;
    private final EventBus eventBus;

    @Override
    public void handle(TransferCommand command) {
        this.ensureUserNotTheSame(command);
        this.ensureDescriptionCorrect(command.getDescription());
        this.ensureCorrectMoney(command.getMoney());
        User userTo = this.ensureUserToExists(command.getUserIdTo());
        this.ensureUserToHasSignUpAllCompletedState(userTo);

        User userFrom = this.usersService.getByUserId(command.getUserIdFrom());
        String currenyCode = currencyService.getByCountryCode(userFrom.getCountry()).getCode();

        this.tryPaymentUserFromToApp(userFrom.getUserId(), command.getMoney(), currenyCode);
        this.tryPaymentAppToUserTo(userTo.getUserId(), command.getMoney(), currenyCode);
    }

    private void tryPaymentUserFromToApp(UUID userIdFrom, double money, String currencyCode){
        try {
            this.paymentMakerService.paymentUserToApp(userIdFrom, money, currencyCode);
        } catch (Exception e) {
            //TODO
        }
    }

    private void tryPaymentAppToUserTo(UUID usaerIdTo, double money, String currencyCode){
        try {
            this.paymentMakerService.paymentAppToUser(usaerIdTo, money, currencyCode);
        } catch (Exception e) {
            //TODO
        }
    }

    private void ensureUserNotTheSame(TransferCommand command){
        if(command.getUserIdFrom().equals(command.getUserIdTo()))
            throw new CannotBeYourself("You cant make a transfer to your self");
    }

    private void ensureDescriptionCorrect(String description) {
        if(description == null || description.equals("") || description.length() > 16)
            throw new IllegalLength("Description should be between 0 and 16");
    }

    private void ensureCorrectMoney(double money) {
        if(money <= 0 || money > 10000)
            throw new IllegalQuantity("Money should be between 0 an 10000");
    }

    private User ensureUserToExists(UUID userIdTo) {
        return this.usersService.getByUserId(userIdTo);
    }

    private void ensureUserToHasSignUpAllCompletedState(User user){
        if(!(user.getState() == UserState.SIGNUP_ALL_COMPLETED))
            throw new IllegalState("User still has to sign up");
    }
}
