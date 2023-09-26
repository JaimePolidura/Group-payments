package es.grouppayments.backend.payments.userpaymentsinfo.changecard.confirm;

import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class ConfirmChangeCardCommand implements Command {
    @Getter private final String token;
}
