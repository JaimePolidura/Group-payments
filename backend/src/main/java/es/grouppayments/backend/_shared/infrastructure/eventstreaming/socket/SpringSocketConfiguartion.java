package es.grouppayments.backend._shared.infrastructure.eventstreaming.socket;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@ConditionalOnProperty(value = "eventsclientdispatcher.method", havingValue = "stomp")
public class SpringSocketConfiguartion implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/group");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // const serverUrl = 'http://localhost:8080/groupevents';
        //    const ws = new SockJS(serverUrl);
        registry.addEndpoint("/groupsevents")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
