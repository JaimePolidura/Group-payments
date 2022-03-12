package es.grouppayments.backend._shared.infrastructure.eventstreaming.sse;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend._shared.infrastructure.eventstreaming.GroupEventClientDispatcher;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SseEventDispatcher implements GroupEventClientDispatcher {
    private final SseSubscribersRegistry subscribersRegistry;

    @Override
    public void send(GroupDomainEvent event) {
        subscribersRegistry.sendToGroup(event.getGroupId(), toJSON(event));
    }

    private String toJSON(GroupDomainEvent event){
        return (new JSONObject(event.toPrimitives()))
                .toString();
    }
}
