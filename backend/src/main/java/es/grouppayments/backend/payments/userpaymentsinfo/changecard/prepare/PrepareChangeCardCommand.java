package es.grouppayments.backend.payments.userpaymentsinfo.changecard.prepare;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class PrepareChangeCardCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final String paymentMethod;
    @Getter private final Dob dob;
}
