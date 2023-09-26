package es.grouppayments.backend.notifications.online.infrastrucutre;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@ConditionalOnProperty(value = "eventsclientdispatcher.method", havingValue = "stomp")
@RequiredArgsConstructor
public class SpringSocketConfiguartion implements WebSocketMessageBrokerConfigurer {
    private final SocketAuthenticationMiddleware authenticationMiddleware;
    @Value("${grouppayments.frontend.route}") private String frontendRoute;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notifications/online/socket")
                .setAllowedOrigins("http://localhost:4200", "http://localhost:8100", "http://localhost")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authenticationMiddleware);
    }
}
