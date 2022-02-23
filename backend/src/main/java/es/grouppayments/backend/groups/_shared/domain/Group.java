package es.grouppayments.backend.groups._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class Group extends Aggregate {
    @Getter private final UUID groupId;
    @Getter private final String title;
    @Getter private final LocalDateTime createdDate;
    @Getter private final double money;
    @Getter private final GroupStatus status;
    @Getter private final UUID adminUserId;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "groupId", groupId.toString(),
                "title", title,
                "createdDate", this.createdDate.toString(),
                "money", money,
                "status", status.toString(),
                "adminUserId", adminUserId.toString()
        );
    }
}
