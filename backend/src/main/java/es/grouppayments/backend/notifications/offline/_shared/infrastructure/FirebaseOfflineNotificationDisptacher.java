package es.grouppayments.backend.notifications.offline._shared.infrastructure;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import es.grouppayments.backend.notifications._shared.domain.OfflineNotificableEvent;
import es.grouppayments.backend.notifications.offline._shared.domain.OfflineNotificationDispatcher;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public final class FirebaseOfflineNotificationDisptacher implements OfflineNotificationDispatcher {
    private final FirebaseMessaging firebaseMessaging;

    @Override
    public void distpach(String token, OfflineNotificableEvent event) {
        Notification notification = Notification
                .builder()
                .setTitle(event.title())
                .setBody(event.message())
                .build();

        Message message = Message
                .builder()
                .setToken(token)
                .putAllData(toMapStringString(event.toPrimitives()))
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> toMapStringString(Map<?, ?> map){
        var newMap = new HashMap<String, String>();

        map.forEach((k, v) -> {
            newMap.put(String.valueOf(k), String.valueOf(v));
        });

        return newMap;
    }
}
