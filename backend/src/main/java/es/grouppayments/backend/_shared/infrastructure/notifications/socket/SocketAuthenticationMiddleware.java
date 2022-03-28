package es.grouppayments.backend._shared.infrastructure.notifications.socket;

import es.grouppayments.backend._shared.infrastructure.auth.JWTUtils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(value = "eventsclientdispatcher.method", havingValue = "stomp")
public class SocketAuthenticationMiddleware implements ChannelInterceptor {
    private static final UserState REQUIRED_STATE = UserState.SIGNUP_ALL_COMPLETED;

    private final JWTUtils jwtUtils;
    private final UsersService usersService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(isConnecting(accessor))
            authenticate(message);

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

        ensureTokenIsValid(token, userId);
        ensureUserHasCorrectState(userId);
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

    private void ensureTokenIsValid(String token, String userId){
        if(!jwtUtils.isValid(token, UUID.fromString(userId)))
            throw new IllegalArgumentException("Illegal token");
    }

    private void ensureUserHasCorrectState(String userId){
        UserState state = this.usersService.getByUserId(UUID.fromString(userId)).getState();

        if(state != REQUIRED_STATE)
            throw new IllegalState("Incorrect state, you should fully sign up");
    }

}
