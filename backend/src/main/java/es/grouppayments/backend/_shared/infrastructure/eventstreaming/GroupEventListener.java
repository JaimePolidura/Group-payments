package es.grouppayments.backend._shared.infrastructure.eventstreaming;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupEventListener {
    private final GroupEventClientDispatcher eventClientDispatcher;

    @SneakyThrows
    @EventListener(GroupDomainEvent.class)
    @Order(1)
    public void onNewEvent(GroupDomainEvent event){
        if(!event.name().equalsIgnoreCase("group-created")){
            this.eventClientDispatcher.send(event);
        }
    }
}
