package es.grouppayments.backend.payments.payments.transfer;

import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.grouppayments.backend.payments.payments.PaymentsTestMother;
import es.grouppayments.backend.users._shared.domain.UsersService;

import java.util.UUID;

public class TransferTestMother extends PaymentsTestMother {
    private final TransferCommandHandler commandHandler;

    public TransferTestMother(){
        this.commandHandler = new TransferCommandHandler(
                new UsersService(super.usersRepository()),
                super.testPaymentMaker(),
                new CurrencyService(super.currencyRepository()),
                super.testEventBus,
                super.commissionPolicy()
        );
    }

    public void execute(UUID userIdFrom, UUID userIdTo, double money){
        this.commandHandler.handle(new TransferCommand(userIdFrom, userIdTo, money, "porro"));
    }

    public void execute(UUID userIdFrom, UUID userIdTo, double money, String description){
        this.commandHandler.handle(new TransferCommand(userIdFrom, userIdTo, money, description));
    }
}
