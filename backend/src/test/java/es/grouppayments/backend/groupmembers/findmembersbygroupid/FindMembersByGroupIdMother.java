package es.grouppayments.backend.groupmembers.findmembersbygroupid;

import _shared.UsingUsers;
import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.UUID;

public class FindMembersByGroupIdMother extends GroupMembersTestMother implements UsingUsers {
    private final FindMembersByGroupIdQueryHandler handler;
    private final UserRepository userRepository;

    public FindMembersByGroupIdMother(){
        this.userRepository = new UserRepositoryInMemory();

        this.handler = new FindMembersByGroupIdQueryHandler(
                new GroupMemberService(groupMemberRepository, testEventBus),
                new UsersService(userRepository)
        );
    }

    public FindMembersByGroupIdQueryResponse executeQuery(UUID gruopId){
        return this.handler.handle(new FindMembersByGroupIdQuery(gruopId));
    }

    @Override
    public UserRepository usersRepository() {
        return userRepository;
    }
}
