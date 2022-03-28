package es.grouppayments.backend._shared.infrastructure.eventstreaming.sse;

import es.grouppayments.backend._shared.infrastructure.ApplicationController;
import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.events.GroupDeleted;
import es.jaime.javaddd.domain.exceptions.IllegalAccess;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
@ConditionalOnProperty(value = "eventsclientdispatcher.method", havingValue = "sse")
public class SseSubscriberController extends ApplicationController {
    private final GroupMemberService groupMemberService;
    private final SseSubscribersRegistry subscribersRegistry;
    private final JWTUtils jwtUtils;

    @RequestMapping("/eventstreaming/sse")
    public SseEmitter sseEmitter(@RequestParam String token,
                                 @RequestParam String userId){
        validateTokenOrThrowException(token, userId);

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        UUID groupIdOfUser = groupMemberService.findGroupMemberByUserIdOrThrowException(UUID.fromString(userId))
                .getGroupId();

        this.subscribersRegistry.add(groupIdOfUser, sseEmitter, UUID.fromString(userId));

        sseEmitter.onCompletion(() -> this.subscribersRegistry.delete(groupIdOfUser, UUID.fromString(userId)));
        sseEmitter.onError(e -> this.subscribersRegistry.delete(groupIdOfUser, UUID.fromString(userId)));
        sseEmitter.onTimeout(() -> this.subscribersRegistry.delete(groupIdOfUser, UUID.fromString(userId)));

        return sseEmitter;
    }

    private void validateTokenOrThrowException(String token, String userId){
        if(!jwtUtils.isValid(token, UUID.fromString(userId)))
            throw new IllegalAccess("Incorrect authentication");
    }

    @EventListener({GroupDeleted.class})
    @Order(2)
    public void onGroupDeleted(GroupDeleted groupDeleted){
        this.subscribersRegistry.delete(groupDeleted.getGroupId());
    }
}
