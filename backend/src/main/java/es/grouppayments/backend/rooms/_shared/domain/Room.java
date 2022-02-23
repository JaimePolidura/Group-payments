package es.grouppayments.backend.rooms._shared.domain;

import es.grouppayments.backend.users._shared.domain.User;
import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Room extends Aggregate {
    @Getter private final UUID roomId;
    @Getter private final String title;
    @Getter private final LocalDateTime createdDate;
    @Getter private final double money;
    @Getter private final RoomStatus status;
    @Getter private final User admin;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "roomId", roomId.toString(),
                "title", title,
                "createdDate", this.createdDate.toString(),
                "money", money,
                "status", status,
                "admin", admin
        );
    }
}
