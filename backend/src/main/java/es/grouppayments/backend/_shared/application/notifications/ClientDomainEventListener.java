package es.grouppayments.backend._shared.application.notifications;

import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend._shared.domain.notifications.NotificationClientDispatcher;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class ClientDomainEventListener {
    private final NotificationClientDispatcher notificationDispatcher;

    @SneakyThrows
    @Order(1)
    @EventListener(NotificableClientDomainEvent.class)
    public void onNewEvent(NotificableClientDomainEvent event){
        event.to().forEach(to -> this.sendNotification(to, event));
    }

    private void sendNotification(UUID to, NotificableClientDomainEvent event){
        this.notificationDispatcher.send(to, event);
    }
}
