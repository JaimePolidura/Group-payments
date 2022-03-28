package es.grouppayments.backend._shared.domain.notifications;

import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;

import java.util.UUID;

public interface NotificationClientDispatcher {
    void send(UUID userId, NotificableClientDomainEvent event);
}
