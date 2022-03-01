package _shared;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;

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
        Optional<UUID> groupMemberOptional = groupMemberRepository().findGroupIdByUserId(userId);

        assertTrue(groupMemberOptional.isPresent() || groupMemberOptional.get().equals(groupId));
    }

    default void assertMemberDeleted(UUID... membersId){
        Arrays.stream(membersId).forEach(memberId -> {
            assertTrue(groupMemberRepository().findGroupIdByUserId(memberId).isEmpty());
        });
    }
}
