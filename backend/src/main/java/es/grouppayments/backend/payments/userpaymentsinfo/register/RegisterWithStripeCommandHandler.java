package es.grouppayments.backend.payments.userpaymentsinfo.register;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.infrastructure.StripeServiceImpl;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class RegisterWithStripeCommandHandler implements CommandHandler<RegisterWithStripeCommand> {
    private final StripeServiceImpl stripeService;

    @Override
    public void handle(RegisterWithStripeCommand command) {
        this.stripeService.createCustomer(command.getUserId(), command.getPaymentMethod());
        this.stripeService.createConnectedAccount(command.getUserId(), command.getDob());
    }
}
