package es.grouppayments.backend.groupmembers._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupMember extends Aggregate {
    @Getter private final UUID userId;
    @Getter private final UUID groupId;
    @Getter private final GroupMemberRole role;

    public boolean isAdmin(){
        return this.role == GroupMemberRole.ADMIN;
    }
}
