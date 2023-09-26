package es.grouppayments.backend.notifications.offline._shared.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserOfflineNotificationRepository {
    void save(UserOfflineNotificationInfo info);

    Optional<UserOfflineNotificationInfo> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
