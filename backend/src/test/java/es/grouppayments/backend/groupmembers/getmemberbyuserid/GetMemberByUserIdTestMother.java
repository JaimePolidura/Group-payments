package es.grouppayments.backend.groupmembers.getmemberbyuserid;


import _shared.users.UsingUsers;
import es.grouppayments.backend.groupmembers.GroupMembersTestMother;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.users.users._shared.domain.UserRepository;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.UUID;

public class GetMemberByUserIdTestMother extends GroupMembersTestMother implements UsingUsers {
    private final GetMemberByUserIdQueryHandler handler;
    private final UserRepository userRepository;

    public GetMemberByUserIdTestMother(){
        this.userRepository = new UserRepositoryInMemory();
        this.handler = new GetMemberByUserIdQueryHandler(
                new UsersService(this.usersRepository(), super.testEventBus),
                new GroupMemberService(groupMemberRepository(), testEventBus)
        );
    }

    public GetMemberByUserIdQueryResponse execute(UUID groupId, UUID userIdToGet, UUID userId){
        return this.handler.handle(new GetMemberByUserIdQuery(userIdToGet, groupId, userId));
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
