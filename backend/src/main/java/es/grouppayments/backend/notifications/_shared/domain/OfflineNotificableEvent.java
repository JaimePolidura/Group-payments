package es.grouppayments.backend.notifications._shared.domain;

public interface OfflineNotificableEvent extends NotificableEvent{
    String title();
    String message();
}
