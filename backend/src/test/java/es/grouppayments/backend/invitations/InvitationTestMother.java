package es.grouppayments.backend.invitations;

import _shared.*;
import _shared.groups.UsingGroupMembers;
import _shared.groups.UsingGroups;
import _shared.invitations.UsingInvitations;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRepository;
import es.grouppayments.backend.groupmembers._shared.infrastructure.GroupMemberRepositoryInMemory;
import es.grouppayments.backend.groups._shared.domain.GroupRepository;
import es.grouppayments.backend.groups._shared.infrastructure.GroupsRepositoryInMemory;
import es.grouppayments.backend.invitations._shared.domain.InvitationRepository;
import es.grouppayments.backend.invitations._shared.infrastructure.InvitationRepositoryInMemory;

public class InvitationTestMother extends TestMother implements UsingInvitations, UsingGroups, UsingGroupMembers {
    private final InvitationRepository invitationRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public InvitationTestMother(){
        this.invitationRepository = new InvitationRepositoryInMemory();
        this.groupRepository = new GroupsRepositoryInMemory();
        this.groupMemberRepository = new GroupMemberRepositoryInMemory();
    }

    @Override
    public InvitationRepository invitationsRepository() {
        return this.invitationRepository;
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
