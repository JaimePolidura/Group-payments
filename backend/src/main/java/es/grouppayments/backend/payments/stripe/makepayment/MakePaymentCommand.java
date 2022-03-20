package es.grouppayments.backend.payments.stripe.makepayment;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public class MakePaymentCommand implements Command {
    @Getter private final UUID gruopId;
    @Getter private final UUID userId;
}
