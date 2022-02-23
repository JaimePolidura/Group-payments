package es.grouppayments.backend.roommembers._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class RoomMember extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final UUID roomId;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "userId", this.userId.toString(),
                "roomId", this.roomId.toString()
        );
    }
}
