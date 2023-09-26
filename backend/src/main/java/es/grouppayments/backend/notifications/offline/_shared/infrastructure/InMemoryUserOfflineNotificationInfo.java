package es.grouppayments.backend.notifications.offline._shared.infrastructure;

import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationInfo;
import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryUserOfflineNotificationInfo implements UserOfflineNotificationRepository {
    private final Map<UUID, UserOfflineNotificationInfo> usersInfo;

    public InMemoryUserOfflineNotificationInfo(){
        this.usersInfo = new ConcurrentHashMap<>();
    }

    @Override
    public void save(UserOfflineNotificationInfo userInfo) {
        this.usersInfo.put(userInfo.getUserId(), userInfo);
    }

    @Override
    public Optional<UserOfflineNotificationInfo> findByUserId(UUID userId) {
        return Optional.ofNullable(this.usersInfo.get(userId));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        this.usersInfo.remove(userId);
    }
}
