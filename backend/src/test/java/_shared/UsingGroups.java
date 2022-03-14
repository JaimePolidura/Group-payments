package _shared;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupState;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public interface UsingGroups extends UsingGroupMembers{
    String DEFAULT_DESCRIPTION = "as";

    GroupRepository groupRepository();

    default void addGroup(UUID groupId, UUID adminUserId, double money, UUID... membersUserId){
        this.groupRepository().save(new Group(groupId, DEFAULT_DESCRIPTION, LocalDateTime.now(), money, adminUserId, GroupState.PROCESS));

        addMember(groupId, adminUserId, GroupMemberRole.ADMIN);
        addMember(groupId, membersUserId);
    }

    default void addGroup(UUID groupId, UUID userId){
        this.groupRepository().save(new Group(groupId, DEFAULT_DESCRIPTION, LocalDateTime.now(), 1, userId, GroupState.PROCESS));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    default void addGroup(UUID groupId, UUID userId, double money){
        this.groupRepository().save(new Group(groupId, DEFAULT_DESCRIPTION, LocalDateTime.now(), money, userId, GroupState.PROCESS));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    default void changeStateTo(UUID groupId, GroupState groupState){
        Group group = this.groupRepository().findById(groupId).get();
        Group changeStateGroup = group.changeStateTo(groupState);
        this.groupRepository().deleteById(groupId);
        this.groupRepository().save(changeStateGroup);
    }

    default void assertGroupCreated(UUID groupId){
        assertTrue(this.groupRepository().findById(groupId).isPresent());
    }

    default void assertGroupDeleted(UUID groupId){
        assertTrue(groupRepository().findById(groupId).isEmpty());
    }

    default <T> void assertContentGroup(UUID groupId, Function<Group, T> contentExtractor, T expected){
        Group groupToCheck = this.groupRepository().findById(groupId).get();
        T dataExtracted = contentExtractor.apply(groupToCheck);

        assertEquals(expected, dataExtracted);
    }
}
