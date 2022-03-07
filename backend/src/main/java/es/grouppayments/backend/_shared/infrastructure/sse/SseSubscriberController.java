package es.grouppayments.backend._shared.infrastructure.sse;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend._shared.infrastructure.Controller;
import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.jaime.javaddd.domain.exceptions.IllegalAccess;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
public class SseSubscriberController extends Controller {
    private final GroupMemberService groupMemberService;
    private final SreSubscribersRegistry subscribersRegistry;
    private final JWTUtils jwtUtils;

    @RequestMapping("/sse")
    public SseEmitter sseEmitter(@RequestParam String token,
                                 @RequestParam String userId) throws IOException {
        validateTokenOrThrowException(token, userId);

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        UUID groupIdOfUser = groupMemberService.findGroupMemberByUserId(UUID.fromString(userId))
                .get()
                .getGroupId();

        this.subscribersRegistry.add(groupIdOfUser, sseEmitter);

        sseEmitter.onCompletion(() -> this.subscribersRegistry.delete(groupIdOfUser, sseEmitter));
        sseEmitter.onError(e -> this.subscribersRegistry.delete(groupIdOfUser, sseEmitter));
        sseEmitter.onTimeout(() -> this.subscribersRegistry.delete(groupIdOfUser, sseEmitter));

        return sseEmitter;
    }

    private void validateTokenOrThrowException(String token, String userId){
        if(!jwtUtils.isValid(token, UUID.fromString(userId)))
            throw new IllegalAccess("Incorrect authentication");
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
