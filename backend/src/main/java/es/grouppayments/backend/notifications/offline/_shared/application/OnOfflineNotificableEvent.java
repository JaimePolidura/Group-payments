package es.grouppayments.backend.notifications.offline._shared.application;

import es.grouppayments.backend._shared.domain.ThreadRunner;
import es.grouppayments.backend.notifications._shared.domain.OfflineNotificableEvent;
import es.grouppayments.backend.notifications.offline._shared.domain.OfflineNotificationDispatcher;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public final class OnOfflineNotificableEvent {
    private final OfflineNotificationDispatcher notificationDispatcher;
    private final UserOfflineNotificationsInfoService userNotificationsInfoService;
    private final ThreadRunner threadRunner;

    @EventListener({OfflineNotificableEvent.class})
    public void on(OfflineNotificableEvent event){
        this.threadRunner.run(() -> {
            event.to().forEach(userIdTo -> this.sendNotification(userIdTo, event));
        });
    }

    private void sendNotification(UUID userId, OfflineNotificableEvent event){
        try{
            this.notificationDispatcher.distpach(
                    this.userNotificationsInfoService.getByUserId(userId).getToken(),
                    event
            );
        }catch (Exception e){
            //TODO User might be on web not in phones.
        }
    }
}
