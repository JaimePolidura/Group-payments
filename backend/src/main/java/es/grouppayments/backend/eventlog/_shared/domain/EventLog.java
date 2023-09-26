package es.grouppayments.backend.eventlog._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class EventLog extends Aggregate {
    @Getter private final UUID eventLogId;
    @Getter private final LocalDateTime date;
    @Getter private final String eventName;
    @Getter private final List<UUID> usersId;
    @Getter private final Map<String, Object> body;
}
