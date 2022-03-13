package es.grouppayments.backend._shared.infrastructure.eventstreaming.socket;

import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthenticationMiddleware implements ChannelInterceptor {
    private final JWTUtils jwtUtils;
    private final GroupMemberService groupMemberService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(isConnecting(accessor)){
            authenticate(message);
        }

        return message;
    }

    private boolean isConnecting(StompHeaderAccessor accessor){
        return StompCommand.CONNECT.equals(accessor.getCommand());
    }

    private void authenticate(Message<?> message){
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        MessageHeaders headers = message.getHeaders();
        ensureAllNecesaryHeadersPresent(accessor, headers);

        String token = getElementFromNativeHeader(accessor, "token");
        String userId = getElementFromNativeHeader(accessor, "userId");
        String groupId = getElementFromNativeHeader(accessor, "groupId");

        authenticateUserOrThrowException(token, userId);
        ensureUserInGroup(userId, groupId);
    }

    private void ensureAllNecesaryHeadersPresent(StompHeaderAccessor accessor, MessageHeaders headers){
        var containsAllHeaders = containsNavtiveHeader(accessor, "token") &&
                containsNavtiveHeader(accessor, "userId") &&
                containsNavtiveHeader(accessor, "groupId");

        if(!containsAllHeaders){
            throw new IllegalArgumentException("Authentication headers missing");
        }
    }

    private boolean containsNavtiveHeader(StompHeaderAccessor accessor, String headerName){
        List<String> header = accessor.getNativeHeader(headerName);

        return header != null && header.size() > 0;
    }

    private String getElementFromNativeHeader(StompHeaderAccessor accessor, String headerName){
        return accessor.getNativeHeader(headerName).get(0);
    }

    private void authenticateUserOrThrowException(String token, String userId){
        if(!jwtUtils.isValid(token, UUID.fromString(userId))){
            throw new IllegalArgumentException("Illegal token");
        }
    }

    private void ensureUserInGroup(String userId, String groupId) {
        var isInGroup = this.groupMemberService.findMembersByGroupId(UUID.fromString(groupId)).stream()
                .anyMatch(groupMember -> groupMember.getUserId().toString().equals(userId));

        if(!isInGroup)
            throw new IllegalArgumentException("Incorrect group");
    }
}
