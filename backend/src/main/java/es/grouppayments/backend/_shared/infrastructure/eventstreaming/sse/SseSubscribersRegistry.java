package es.grouppayments.backend._shared.infrastructure.eventstreaming.sse;

import es.grouppayments.backend._shared.domain.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseSubscribersRegistry {
    private final Map<UUID, Set<EventSourcingSession>> subscribers;

    public SseSubscribersRegistry(){
        this.subscribers = new ConcurrentHashMap<>();
    }

    public void add(UUID groupId, SseEmitter emitter, UUID userId){
        if(this.subscribers.containsKey(groupId)){
            Set<EventSourcingSession> subscribers = this.subscribers.get(groupId);
            subscribers.removeIf(session -> session.getUserId().equals(userId));
            subscribers.add(new EventSourcingSession(emitter, userId));

            this.subscribers.put(groupId, subscribers);
        }else{
            this.subscribers.put(groupId, Utils.setOf(new EventSourcingSession(emitter, userId)));
        }
    }

    public void delete(UUID groupId, UUID userId){
        this.subscribers.get(groupId).removeIf(session -> session.getUserId().equals(userId));
    }

    public void delete(UUID groupId){
        this.subscribers.remove(groupId);
    }

    @SneakyThrows
    public void sendToGroup(UUID groupId, String data){
        for (EventSourcingSession session : this.subscribers.get(groupId)) {
            session.send(data);
        }
    }

    @AllArgsConstructor
    private static class EventSourcingSession {
        @Getter private final SseEmitter emitter;
        @Getter private final UUID userId;

        public void send(String data) throws IOException {
            this.emitter.send(SseEmitter.event().data(data));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EventSourcingSession that = (EventSourcingSession) o;
            return Objects.equals(userId, that.userId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId);
        }
    }
}
