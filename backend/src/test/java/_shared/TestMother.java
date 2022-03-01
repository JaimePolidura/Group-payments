package _shared;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMother implements UsingTestEventBus{
    protected TestEventBus testEventBus;

    public TestMother(){
        this.testEventBus = new FakeEventBus();
    }

    @Override
    public TestEventBus testEventBus() {
        return this.testEventBus;
    }

    protected void assertEmptyCollection(Collection<?> collection){
        assertTrue(collection.isEmpty());
    }

    protected <T> void assertContentListMatches(List<T> list, Predicate<T> matcher){
        assertTrue(list.stream().allMatch(matcher));
    }

    protected void assertCollectionSize(Collection<?> collection, int size){
        assertEquals(collection.size(), size);
    }
}
