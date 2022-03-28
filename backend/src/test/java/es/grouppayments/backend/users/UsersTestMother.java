package es.grouppayments.backend.users;

import _shared.UsingUsers;
import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

public class UsersTestMother implements UsingUsers {
    private final UserRepository userRepository;

    public UsersTestMother(){
        this.userRepository = new UserRepositoryInMemory();
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
