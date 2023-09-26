package es.grouppayments.backend.eventlog._shared.application;

import es.grouppayments.backend.eventlog._shared.domain.EventLog;
import es.grouppayments.backend.eventlog._shared.domain.EventLogRepository;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class EventLogService {
    private final EventLogRepository eventLogService;

    public void save(EventLog event) {
        this.eventLogService.save(event);
    }

    public EventLog getById(UUID eventLogId) {
        return this.eventLogService.findById(eventLogId)
                .orElseThrow(() -> new ResourceNotFound("Log not found for that ID"));
    }

    public List<EventLog> findByEventName(String eventName) {
        return this.eventLogService.findByEventName(eventName);
    }

    public List<EventLog> findByUserId(UUID userId) {
        return this.eventLogService.findByUserId(userId);
    }
}
