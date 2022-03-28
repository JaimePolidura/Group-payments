package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "grouppayments.printallgroupdomainevents", havingValue = "true")
public final class AllGroupDomainEventListener {
    @EventListener({GroupDomainEvent.class})
    public void on(GroupDomainEvent event){
        System.out.printf("[Event %s]: %s \n", event.name(), (new JSONObject(event.body())));
    }
}
