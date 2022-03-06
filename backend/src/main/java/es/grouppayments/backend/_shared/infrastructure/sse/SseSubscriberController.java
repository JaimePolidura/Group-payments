package es.grouppayments.backend._shared.infrastructure.sse;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
public class SseSubscriberController extends Controller {
    private final GroupMemberService groupMemberService;
    private final SreSubscribersRegistry subscribersRegistry;

    public SseSubscriberController(GroupMemberService groupMemberService, SreSubscribersRegistry subscribersRegistry){
        this.groupMemberService = groupMemberService;
        this.subscribersRegistry = subscribersRegistry;
    }

    @RequestMapping("/sse")
    public SseEmitter sseEmitter() throws IOException {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        UUID groupIdOfUser = groupMemberService.findGroupMemberByUserId(getLoggedUsername())
                .get()
                .getGroupId();

        this.subscribersRegistry.add(groupIdOfUser, sseEmitter);

        return sseEmitter;
    }

    @SneakyThrows
    @EventListener(GroupDomainEvent.class)
    public void onNewEvent(GroupDomainEvent event){
        if(!event.name().equalsIgnoreCase("group-created")){
            this.subscribersRegistry.sendToGroup(event.getGroupId(), toJSON(event));
        }
    }

    private String toJSON(GroupDomainEvent event){
        return (new JSONObject(event.toPrimitives()))
                .toString();
    }
}
