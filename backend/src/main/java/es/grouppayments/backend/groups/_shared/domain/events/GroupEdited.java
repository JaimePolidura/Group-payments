package es.grouppayments.backend.groups._shared.domain.events;

import es.grouppayments.backend._shared.domain.GroupDomainEvent;
import es.grouppayments.backend.groups._shared.domain.Group;

import java.util.Map;
import java.util.UUID;

public class GroupEdited extends GroupDomainEvent {
    private final Group group;

    public GroupEdited(Group group) {
        this.group = group;
    }

    @Override
    public UUID getGroupId() {
        return this.group.getGroupId();
    }

    @Override
    public String name() {
        return "group-edited";
    }

    @Override
    public Map<String, Object> body() {
        return Map.of("group", this.group.toPrimitives());
    }
}
