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
    @Getter private final String description;
    @Getter private final LocalDateTime createdDate;
    @Getter private final double money;
    @Getter private final UUID adminUserId;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "groupId", groupId.toString(),
                "description", description,
                "createdDate", this.createdDate.toString(),
                "money", money,
                "adminUserId", adminUserId.toString()
        );
    }

    public Group changeMoney(double newMoney){
        return new Group(this.groupId, this.description, this.createdDate, newMoney, this.adminUserId);
    }

    public Group changeDescription(String newDescription){
        return new Group(this.groupId, newDescription, this.createdDate, this.money, this.adminUserId);
    }
}
