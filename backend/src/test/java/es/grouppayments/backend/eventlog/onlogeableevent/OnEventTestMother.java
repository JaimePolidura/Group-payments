package es.grouppayments.backend.eventlog.onlogeableevent;

import es.grouppayments.backend.eventlog.EventLogTestMother;
import es.grouppayments.backend.eventlog._shared.application.EventLogService;
import es.grouppayments.backend.eventlog._shared.domain.LogeableEvent;

public class OnEventTestMother extends EventLogTestMother {
    private final OnLogeableEvent onlogeableevent;

    public OnEventTestMother(){
        this.onlogeableevent = new OnLogeableEvent(
                new EventLogService(super.eventLogRepository())
        );
    }

    public void on(LogeableEvent event){
        this.onlogeableevent.on(event);
    }
}
