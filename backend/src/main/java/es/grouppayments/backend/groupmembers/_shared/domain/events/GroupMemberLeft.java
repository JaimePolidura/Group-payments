package es.grouppayments.backend.groupmembers._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class GroupMemberLeft extends GroupDomainEvent {
    private final UUID userId;
    private final UUID groupId;

    @Override
    public UUID getGroupId() {
        return this.groupId;
    }

    @Override
    public String name() {
        return "group-member-left";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of(
                "userId", userId,
                "groupId", groupId
        );
    }
}
