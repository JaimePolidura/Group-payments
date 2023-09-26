package es.grouppayments.backend.eventlog._shared.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventLogRepository {
    void save(EventLog event);

    Optional<EventLog> findById(UUID eventLogId);

    List<EventLog> findByEventName(String eventName);

    List<EventLog> findByUserId(UUID userId);
}
