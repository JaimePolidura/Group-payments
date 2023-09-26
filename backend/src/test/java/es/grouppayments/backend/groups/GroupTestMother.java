package es.grouppayments.backend.groups;

import _shared.TestMother;
import _shared.groups.UsingGroups;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;

public class GroupTestMother extends TestMother implements UsingGroups {
    private GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupTestMother(){
        this.groupRepository = new GroupsRepositoryInMemory();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
    }

    @Override
    public GroupRepository groupRepository() {
        return this.groupRepository;
    }

    @Override
    public GroupMemberRepository groupMemberRepository() {
        return this.groupMemberRepository;
    }

    public void clearGroupsRepository(){
        this.groupRepository = new GroupsRepositoryInMemory();
    }
}
