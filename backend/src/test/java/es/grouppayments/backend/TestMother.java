package es.grouppayments.backend;

import _shared.FakeEventBus;
import _shared.TestEventBus;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupStatus;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;
import es.jaime.javaddd.domain.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMother {
    protected GroupRepository groupRepository;
    protected TestEventBus testEventBus;
    protected GroupMemberRepository groupMemberRepository;

    public TestMother(){
        this.groupRepository = new GroupsRepositoryInMemory();
        this.testEventBus = new FakeEventBus();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
    }

    protected void addGroup(UUID groupId, UUID userId){
        this.groupRepository.save(new Group(groupId, "as", LocalDateTime.now(), 1, GroupStatus.CREATED, userId));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    protected void addGroup(UUID groupId, UUID userId, double money){
        this.groupRepository.save(new Group(groupId, "as", LocalDateTime.now(), money, GroupStatus.CREATED, userId));

        addMember(groupId, userId, GroupMemberRole.ADMIN);
    }

    protected void addMember(UUID groupId, UUID userId){
        this.groupMemberRepository.save(new GroupMember(userId, groupId, GroupMemberRole.USER));
    }

    protected void addMember(UUID groupId, UUID userId, GroupMemberRole role){
        this.groupMemberRepository.save(new GroupMember(userId, groupId, role));
    }

    protected void assertGroupCreated(UUID groupId){
        assertTrue(this.groupRepository.findById(groupId).isPresent());
    }

    protected void assertMemberInGroup(UUID groupId, UUID userId){
        Optional<UUID> groupMemberOptional = groupMemberRepository.findGroupIdByUserId(userId);

        assertTrue(groupMemberOptional.isPresent() || groupMemberOptional.get().equals(groupId));
    }

    protected void assertGroupDeleted(UUID groupId){
        assertTrue(this.groupRepository.findById(groupId).isEmpty());
    }

    @SafeVarargs
    protected final void assertEventRaised(Class<? extends DomainEvent>... events){
        Arrays.stream(events).forEach(event -> assertTrue(testEventBus.isRaised(event)));
    }

    protected <T extends DomainEvent> void assertContentOfEventEquals(Class<T> event, Function<T, Object> dataAccessor, Object toEq){
        assertEquals(dataAccessor.apply((T) testEventBus.getEvent(event)), toEq);
    }
}
