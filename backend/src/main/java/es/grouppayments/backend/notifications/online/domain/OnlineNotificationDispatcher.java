package es.grouppayments.backend.notifications.online.domain;

import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;

import java.util.UUID;

public interface OnlineNotificationDispatcher {
    void send(UUID userId, OnlineNotificableClientEvent event);
}
