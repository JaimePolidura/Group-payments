package es.grouppayments.backend._shared.domain.messages;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public interface AsyncMessage {
    AsyncMessageType type();
    String name();
    Map<String, Object> body();

    default Map<String, Object> meta() {
        return Map.of();
    }

    default Map<String, Object> toPrimitives(){
        return Map.of(
                "id", UUID.randomUUID(),
                "date", LocalDateTime.now(),
                "type", type().toString(),
                "name", name(),
                "body", body(),
                "meta", meta()
        );
    }
}
