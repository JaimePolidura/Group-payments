package _shared.groups;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public interface UsingGroupMembers {
    GroupMemberRepository groupMemberRepository();

    default void addMember(UUID groupId, UUID... usersId){
        Arrays.stream(usersId).forEach(userId -> {
            this.groupMemberRepository().save(new GroupMember(userId, groupId, GroupMemberRole.USER));
        });
    }

    default void addMember(UUID groupId, UUID userId, GroupMemberRole role){
        this.groupMemberRepository().save(new GroupMember(userId, groupId, role));
    }

    default void assertMemberInGroup(UUID groupId, UUID userId){
        Optional<GroupMember> groupMemberOptional = groupMemberRepository().findGroupMemberByUserId(userId);

        assertTrue(groupMemberOptional.isPresent() || groupMemberOptional.get().getGroupId().equals(groupId));
    }

    default void assertMemberDeleted(UUID... membersId){
        Arrays.stream(membersId).forEach(memberId -> {
            assertTrue(groupMemberRepository().findGroupMemberByUserId(memberId).isEmpty());
        });
    }
}
