package es.grouppayments.backend.eventlog.onlogeableevent;

import es.grouppayments.backend.eventlog._shared.application.EventLogService;
import es.grouppayments.backend.eventlog._shared.domain.EventLog;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public final class OnLogeableEvent {
    private final EventLogService eventLogService;

    @EventListener({LogeableEvent.class})
    public void on(LogeableEvent event){
        this.eventLogService.save(new EventLog(
                event.id(),
                event.date(),
                event.name(),
                event.to(),
                event.body()
        ));
    }
}
