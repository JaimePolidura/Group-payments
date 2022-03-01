package _shared;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public interface UsingGroups extends UsingGroupMembers{
    GroupRepository groupRepository();

    default void addGroup(UUID groupId, UUID adminUserId, double money, UUID... membersUserId){
        this.groupRepository().save(new Group(groupId, "as", LocalDateTime.now(), money, adminUserId));

        addMember(groupId, adminUserId, GroupMemberRole.ADMIN);
        addMember(groupId, membersUserId);
    }

    default void addGroup(UUID groupId, UUID userId){
        this.groupRepository().save(new Group(groupId, "as", LocalDateTime.now(), 1, userId));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    default void addGroup(UUID groupId, UUID userId, double money){
        this.groupRepository().save(new Group(groupId, "as", LocalDateTime.now(), money, userId));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    default void assertGroupCreated(UUID groupId){
        assertTrue(this.groupRepository().findById(groupId).isPresent());
    }

    default void assertGroupDeleted(UUID groupId){
        assertTrue(groupRepository().findById(groupId).isEmpty());
    }
}
