package es.grouppayments.backend.payments.userpaymentsinfo.register;

import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.Dob;
import es.jaime.javaddd.domain.cqrs.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public final class RegisterWithStripeCommand implements Command {
    @Getter private final UUID userId;
    @Getter private final String paymentMethod;
    @Getter private final Dob dob;
}
