package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend._shared.domain.events.GroupDomainEvent;
import es.jaime.javaddd.domain.async.AsyncDomainEvent;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "grouppayments.printalldomainevents", havingValue = "true")
public final class AllDomainEventListener {
    @EventListener({AsyncDomainEvent.class})
    public void on(GroupDomainEvent event){
        System.out.printf("[Event %s]: %s \n", event.name(), (new JSONObject(event.body())));
    }
}
