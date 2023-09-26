package es.grouppayments.backend.notifications.offline._shared.application;

import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationInfo;
import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationRepository;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class UserOfflineNotificationsInfoService {
    private final UserOfflineNotificationRepository repository;

    public UserOfflineNotificationsInfoService(UserOfflineNotificationRepository repository) {
        this.repository = repository;
    }

    public void save(UserOfflineNotificationInfo info){
        this.repository.save(info);
    }

    public UserOfflineNotificationInfo getByUserId(UUID userId){
        return this.repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("User notification info not found"));
    }

    public void deleteByUserId(UUID userId){
        this.repository.deleteByUserId(userId);
    }

}
