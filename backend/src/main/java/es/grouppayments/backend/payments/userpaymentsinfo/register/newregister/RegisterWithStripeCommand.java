package es.grouppayments.backend.payments.userpaymentsinfo.register.newregister;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class RegisterWithStripeCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final String paymentMethod;
}
