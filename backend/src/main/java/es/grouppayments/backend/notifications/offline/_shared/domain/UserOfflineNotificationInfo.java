package es.grouppayments.backend.notifications.offline._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
public final class UserOfflineNotificationInfo extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final String token;
}
