package es.grouppayments.backend.gruops;

import _shared.FakeEventBus;
import _shared.TestEventBus;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.groups._shared.domain.GroupStatus;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;
import es.grouppayments.backend.groups.create.CreateGroupCommand;
import es.grouppayments.backend.groups.create.CreateGroupCommandHandler;
import es.jaime.javaddd.domain.event.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class CreateGroupTestMother {
    protected GroupRepository groupRepository;
    protected TestEventBus testEventBus;
    protected GroupMemberRepository groupMemberRepository;
    protected CreateGroupCommandHandler createGroupCommandHandler;

    public CreateGroupTestMother(){
        this.groupRepository = new GroupsRepositoryInMemory();
        this.testEventBus = new FakeEventBus();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();

        this.createGroupCommandHandler = new CreateGroupCommandHandler(
                new GroupService(this.groupRepository, this.testEventBus),
                new GroupMemberService(this.groupMemberRepository, this.testEventBus)
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, UUID.randomUUID(), 100, "group")
        );
    }

    protected void executeCreateGroupCommandHandler(UUID idOfGroupToCreate, UUID userId){
        this.createGroupCommandHandler.handle(
                new CreateGroupCommand(idOfGroupToCreate, userId, 100, "group")
        );
    }

    protected void addGroup(UUID groupId, UUID userId){
        this.groupRepository.save(new Group(groupId, "as", LocalDateTime.now(), 1, GroupStatus.CREATED, userId));
    }

    protected void addMember(UUID groupId, UUID userId){
        this.groupMemberRepository.save(new GroupMember(userId, groupId));
    }

    protected void assertGroupCreated(UUID groupId){
        assertTrue(this.groupRepository.findById(groupId).isPresent());
    }

    protected void assertEventRaised(Class<? extends DomainEvent> event){
        assertTrue(this.testEventBus.isRaised(event));
    }

}
