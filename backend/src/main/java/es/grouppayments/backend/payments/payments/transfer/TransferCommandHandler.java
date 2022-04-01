package es.grouppayments.backend.payments.payments.transfer;

import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.grouppayments.backend.payments.payments._shared.domain.CommissionPolicy;
import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.ErrorWhileMakingTransfer;
import es.grouppayments.backend.payments.payments._shared.domain.events.transfer.TransferDone;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.CannotBeYourself;
import es.jaime.javaddd.domain.exceptions.IllegalLength;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
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
    private final CommissionPolicy comimssionPolicy;

    @Override
    public void handle(TransferCommand command) {
        this.ensureUserNotTheSame(command);
        this.ensureDescriptionCorrect(command.getDescription());
        this.ensureCorrectMoney(command.getMoney());
        User userTo = this.ensureUserToExists(command.getUserIdTo());
        this.ensureUserToHasSignUpAllCompletedState(userTo);

        User userFrom = this.usersService.getByUserId(command.getUserIdFrom());
        String currenyCode = currencyService.getByCountryCode(userFrom.getCountry()).getCode();

        try {
            this.paymentMakerService.makePayment(userFrom.getUserId(), userTo.getUserId(), command.getMoney(), currenyCode);

            String userNameFrom = this.usersService.getByUserId(command.getUserIdFrom()).getUsername();
            double moneyDeductedCommission = this.comimssionPolicy.deductCommission(command.getMoney());

            this.eventBus.publish(new TransferDone(command.getUserIdFrom(), userNameFrom, command.getUserIdTo(), command.getMoney(),
                    currenyCode, command.getDescription()));
        } catch (Exception e) {
            this.eventBus.publish(new ErrorWhileMakingTransfer(command.getUserIdFrom(), e.getMessage()));
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

    private record PaymentState(boolean isSucess, String reasonOfFailure){}
}
