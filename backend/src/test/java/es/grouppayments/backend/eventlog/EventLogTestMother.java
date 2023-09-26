package es.grouppayments.backend.eventlog;

import _shared.TestMother;
import _shared.eventlog.UsignEventLogs;
import es.grouppayments.backend.eventlog._shared.domain.EventLogRepository;
import es.grouppayments.backend.eventlog._shared.infrastructure.InMemoryEventLogRepository;

public class EventLogTestMother extends TestMother implements UsignEventLogs {
    private final EventLogRepository repository;

    public EventLogTestMother(){
        this.repository = new InMemoryEventLogRepository();
    }

    @Override
    public EventLogRepository eventLogRepository() {
        return this.repository;
    }
}
