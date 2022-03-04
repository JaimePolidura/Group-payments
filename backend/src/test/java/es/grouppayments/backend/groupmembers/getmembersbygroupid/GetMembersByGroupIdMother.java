package es.grouppayments.backend.groupmembers.getmembersbygroupid;

import _shared.UsingUsers;
import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.UUID;

public class GetMembersByGroupIdMother extends GroupMembersTestMother implements UsingUsers {
    private final GetMembersByGroupIdQueryHandler handler;
    private final UserRepository userRepository;

    public GetMembersByGroupIdMother(){
        this.userRepository = new UserRepositoryInMemory();

        this.handler = new GetMembersByGroupIdQueryHandler(
                new GroupMemberService(groupMemberRepository, testEventBus),
                new UsersService(userRepository)
        );
    }

    public GetMembersByGroupIdQueryResponse executeQuery(UUID gruopId){
        return this.handler.handle(new GetMembersByGroupIdQuery(gruopId));
    }

    @Override
    public UserRepository usersRepository() {
        return userRepository;
    }
}
