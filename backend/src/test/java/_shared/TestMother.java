package _shared;

import _shared.eventbus.FakeTestEventBus;
import _shared.eventbus.TestEventBus;
import _shared.eventbus.UsingTestEventBus;
import _shared.users.UsingUsers;
import es.grouppayments.backend.users.users._shared.domain.UserRepository;
import es.grouppayments.backend.users.users._shared.infrastructure.UserRepositoryInMemory;
import es.grouppayments.backend.users.usersimage._shared.domain.UserImagesRepository;
import es.grouppayments.backend.users.usersimage._shared.infrastructure.InMemoryUserImageRepository;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.*;

public class TestMother implements UsingTestEventBus, UsingUsers {
    protected TestEventBus testEventBus;
    private final UserRepository userRepository;
    private final UserImagesRepository userImagesRepository;

    public TestMother(){
        this.testEventBus = new FakeTestEventBus();
        this.userRepository = new UserRepositoryInMemory();
        this.userImagesRepository = new InMemoryUserImageRepository();
    }

    @Override
    public TestEventBus testEventBus() {
        return this.testEventBus;
    }

    protected void assertEmptyCollection(Collection<?> collection){
        assertTrue(collection.isEmpty());
    }

    protected void assertNotEmptyCollection(Collection<?> collection){
        assertTrue(collection.size() > 0);
    }

    protected <T> void assertContentListMatches(List<T> list, Predicate<T> matcher){
        assertTrue(list.stream().allMatch(matcher));
    }

    protected <T> void assertAnyMatch(List<T> list, Predicate<T> matcher){
        assertTrue(list.stream().anyMatch(matcher));
    }

    protected void assertCollectionSize(Collection<?> collection, int size){
        assertEquals(collection.size(), size);
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }

    @Override
    public UserImagesRepository userImagesRepository() {
        return this.userImagesRepository;
    }
}
