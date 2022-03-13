package es.grouppayments.backend._shared.infrastructure.eventstreaming.socket;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend._shared.infrastructure.eventstreaming.GroupEventClientDispatcher;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ConditionalOnProperty(value = "eventsclientdispatcher.method", havingValue = "stomp")
public class SocketEventDispatcher implements GroupEventClientDispatcher {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void send(GroupDomainEvent event) {
        simpMessagingTemplate.convertAndSend(
                getRouteToRedirect(event),
                toJSON(event)
        );
    }

    private String getRouteToRedirect(GroupDomainEvent event){
        return String.format("/group/%s", event.getGroupId());
    }

    private String toJSON(GroupDomainEvent event){
        return (new JSONObject(event.toPrimitives())).toString();
    }
}
