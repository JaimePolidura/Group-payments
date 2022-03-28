package es.grouppayments.backend._shared.infrastructure.notifications.socket;

import es.grouppayments.backend._shared.domain.events.NotificableClientDomainEvent;
import es.grouppayments.backend._shared.domain.notifications.NotificationClientDispatcher;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class SocketNotificationsClientDispatcher implements NotificationClientDispatcher {
    private final SimpMessagingTemplate socketSender;

    @Override
    public void send(UUID userId, NotificableClientDomainEvent event) {
        try{
            this.socketSender.convertAndSend(
                    getRouteToNotificate(userId),
                    eventToJSON(event)
            );
        }catch (Exception e) {
            //Ignore
        }
    }

    private String getRouteToNotificate(UUID userIdToSend){
        return String.format("/user/%s", userIdToSend);
    }

    private String eventToJSON(NotificableClientDomainEvent event){
        return (new JSONObject(event.toPrimitives())).toString();
    }
}
