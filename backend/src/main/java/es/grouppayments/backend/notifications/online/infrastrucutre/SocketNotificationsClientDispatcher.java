package es.grouppayments.backend.notifications.online.infrastrucutre;

import es.grouppayments.backend.notifications._shared.domain.OnlineNotificableClientEvent;
import es.grouppayments.backend.notifications.online.domain.OnlineNotificationDispatcher;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class SocketNotificationsClientDispatcher implements OnlineNotificationDispatcher {
    private final SimpMessagingTemplate socketSender;

    @Override
    public void send(UUID userId, OnlineNotificableClientEvent event) {
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

    private String eventToJSON(OnlineNotificableClientEvent event){
        return (new JSONObject(event.toPrimitives())).toString();
    }
}
