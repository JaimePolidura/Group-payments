package es.grouppayments.backend.gruopsmembers._shared.domain;

import es.grouppayments.backend.gruopsmembers._shared.domain.events.GroupMemberLeft;
import es.jaime.javaddd.domain.event.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMembers;
    private final EventBus eventBus;

    public void save(GroupMember groupMember){
        this.groupMembers.save(groupMember);
    }

    public List<GroupMember> findMembersByGroupId(UUID groupId){
        return this.groupMembers.findMembersByGroupId(groupId);
    }

    public Optional<UUID> findGroupIdByUserId(UUID userId){
        return this.groupMembers.findGroupIdByUserId(userId);
    }

    public void deleteByUserId(UUID userId) {
        UUID groupId = this.findGroupIdByUserId(userId).get();

        this.groupMembers.deleteByUserId(userId);

        eventBus.publish(new GroupMemberLeft(userId, groupId));
    }
}
