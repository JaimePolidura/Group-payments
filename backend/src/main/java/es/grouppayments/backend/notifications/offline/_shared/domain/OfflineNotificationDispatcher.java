package es.grouppayments.backend.notifications.offline._shared.domain;

import es.grouppayments.backend.notifications._shared.domain.OfflineNotificableEvent;

import java.util.UUID;

public interface OfflineNotificationDispatcher {
    void distpach(String token, OfflineNotificableEvent event);
}
