package es.grouppayments.backend.notifications.offline.register;

import es.grouppayments.backend.notifications.offline._shared.application.UserOfflineNotificationsInfoService;
import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationInfo;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public final class RegisterOfflineNotificationInfoControllerHandler implements CommandHandler<RegisterOfflineNotificationInfoCommand> {
    private final UserOfflineNotificationsInfoService userOfflineNotificationsInfoService;

    @Override
    public void handle(RegisterOfflineNotificationInfoCommand command) {
        this.userOfflineNotificationsInfoService.save(new UserOfflineNotificationInfo(
                command.getUserId(),
                command.getToken()
        ));

        System.out.println(command.getToken());
    }
}
