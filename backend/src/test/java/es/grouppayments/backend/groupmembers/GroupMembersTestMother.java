package es.grouppayments.backend.groupmembers;

import _shared.TestMother;
import _shared.groups.UsingGroups;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;

public class GroupMembersTestMother extends TestMother implements UsingGroups {
    protected final GroupMemberRepository groupMemberRepository;
    protected final GroupRepository groupRepository;

    public GroupMembersTestMother(){
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
        this.groupRepository = new GroupsRepositoryInMemory();
    }

    @Override
    public GroupMemberRepository groupMemberRepository() {
        return this.groupMemberRepository;
    }

    @Override
    public GroupRepository groupRepository() {
        return this.groupRepository;
    }
}
