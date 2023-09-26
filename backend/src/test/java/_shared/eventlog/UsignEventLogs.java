package _shared.eventlog;

import es.grouppayments.backend.eventlog._shared.domain.EventLog;
import es.grouppayments.backend.eventlog._shared.domain.EventLogRepository;

import java.util.UUID;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public interface UsignEventLogs {
    EventLogRepository eventLogRepository();

    default void assertEventLogExistsByUsername(UUID userId){
        assertFalse(this.eventLogRepository().findByUserId(userId).isEmpty());
    }

    default void assertEventLogContent(UUID userId, Predicate<EventLog> contentCondition){
        assertTrue(contentCondition.test(this.eventLogRepository().findByUserId(userId).get(0)));
    }
}
