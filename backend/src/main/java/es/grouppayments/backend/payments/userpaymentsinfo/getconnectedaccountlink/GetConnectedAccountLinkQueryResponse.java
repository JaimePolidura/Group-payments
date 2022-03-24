package es.grouppayments.backend.payments.userpaymentsinfo.getconnectedaccountlink;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class GetConnectedAccountLinkQuery {
    @Getter private final UUID userId;
}
