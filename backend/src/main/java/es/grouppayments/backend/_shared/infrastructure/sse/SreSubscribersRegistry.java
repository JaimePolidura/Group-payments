package es.grouppayments.backend._shared.infrastructure.sse;

import es.grouppayments.backend._shared.domain.Utils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

@Service
public class SreSubscribersRegistry {
    private final Map<UUID, Set<SseEmitter>> subscribers;

    public SreSubscribersRegistry(){
        this.subscribers = new HashMap<>();
    }

    public void add(UUID groupId, SseEmitter subscriber){
        if(this.subscribers.containsKey(groupId)){
            Set<SseEmitter> subscribers = this.subscribers.get(groupId);
            subscribers.add(subscriber);

            this.subscribers.put(groupId, subscribers);
        }else{
            this.subscribers.put(groupId, Utils.setOf(subscriber));
        }
    }

    public void delete(UUID groupId, SseEmitter emitter){
        this.subscribers.get(groupId).removeIf(e -> e.equals(emitter));
    }

    public void delete(UUID groupId){
        this.subscribers.remove(groupId);
    }

    @SneakyThrows
    public void sendToGroup(UUID groupId, String data){
        for (SseEmitter sseEmitter : this.subscribers.get(groupId)) {
            sseEmitter.send(SseEmitter.event().data(data));
        }
    }
}
