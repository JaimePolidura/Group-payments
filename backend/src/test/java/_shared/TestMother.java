package _shared;

import es.grouppayments.backend.users._shared.domain.UserRepository;
import es.grouppayments.backend.users._shared.infrastructure.UserRepositoryInMemory;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMother implements UsingTestEventBus, UsingUsers{
    protected TestEventBus testEventBus;
    private final UserRepository userRepository;

    public TestMother(){
        this.testEventBus = new FakeTestEventBus();
        this.userRepository = new UserRepositoryInMemory();
    }

    @Override
    public TestEventBus testEventBus() {
        return this.testEventBus;
    }

    protected void assertEmptyCollection(Collection<?> collection){
        assertTrue(collection.isEmpty());
    }

    protected void assertCollectionNotEmpty(Collection<?> collection){
        assertTrue(collection.size() > 0);
    }

    protected <T> void assertContentListMatches(List<T> list, Predicate<T> matcher){
        assertTrue(list.stream().allMatch(matcher));
    }

    protected void assertCollectionSize(Collection<?> collection, int size){
        assertEquals(collection.size(), size);
    }

    @Override
    public UserRepository usersRepository() {
        return this.userRepository;
    }
}
