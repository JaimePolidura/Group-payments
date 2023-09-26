package es.grouppayments.backend.payments.payments.transfer;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class TransferCommand implements Command {
    @Getter private final UUID userIdFrom;
    @Getter private final UUID userIdTo;
    @Getter private final double money;
    @Getter private final String description;
}
