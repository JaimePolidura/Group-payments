package es.grouppayments.backend.groupmembers._shared.domain;

import es.grouppayments.backend._shared.domain.Utils;
import es.grouppayments.backend.groupmembers._shared.domain.events.GroupMemberLeft;
import es.grouppayments.backend.payments.payments._shared.domain.events.GroupPaymentDone;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static es.grouppayments.backend._shared.domain.Utils.*;

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

    public GroupMember findGroupMemberByUserIdOrThrowException(UUID userId){
        return this.groupMembers.findGroupMemberByUserId(userId)
                .orElseThrow(() -> new ResourceNotFound("Group member for that id not found"));
    }

    public void deleteByUserId(UUID userId) {
        UUID groupId = this.findGroupMemberByUserIdOrThrowException(userId)
                .getGroupId();

        this.groupMembers.deleteByUserId(userId);

        eventBus.publish(new GroupMemberLeft(userId, groupId));
    }

    public void deleteByGroupId(UUID groupId) {
        this.groupMembers.deleteByGroupId(groupId);
    }

    public void leaveGroupIfMember(UUID userId){
        Optional<UUID> groupMemberOptional = this.groupMembers.findGroupMemberByUserId(userId)
                .map(GroupMember::getGroupId);
        boolean isMemberOfGroup = groupMemberOptional.isPresent();

        if(isMemberOfGroup){
            deleteByUserId(userId);
        }
    }
}
