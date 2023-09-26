package es.grouppayments.backend.groups._shared.domain;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
public class Group extends Aggregate {
    @Getter private final UUID groupId;
    @Getter private final String description;
    @Getter private final LocalDateTime createdDate;
    @Getter private final double money;
    @Getter private final UUID adminUserId;
    @Getter private final GroupState state;
    @Getter private final Currency currency;

    @Override
    public Map<String, Object> toPrimitives() {
        return Map.of(
                "groupId", groupId.toString(),
                "description", description,
                "createdDate", this.createdDate.toString(),
                "money", money,
                "adminUserId", adminUserId.toString(),
                "state", state,
                "currency", currency.toMap()
        );
    }

    public Group changeMoney(double newMoney){
        return new Group(this.groupId, this.description, this.createdDate, newMoney, this.adminUserId, this.state, currency);
    }

    public Group changeDescription(String newDescription){
        return new Group(this.groupId, newDescription, this.createdDate, this.money, this.adminUserId, this.state, currency);
    }

    public Group changeStateTo(GroupState newState){
        return new Group(this.groupId, this.description, this.createdDate, this.money, this.adminUserId, newState, currency);
    }

    public boolean isEditable(){
        return this.state.isEditable();
    }

    public boolean canMakePayments(){
        return this.state.canMakePayments();
    }

    public boolean canMembersJoinLeave(){
        return this.state.canMembersJoinLeave();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId);
    }
}
