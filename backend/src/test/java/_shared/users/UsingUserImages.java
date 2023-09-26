package _shared.users;

import es.grouppayments.backend.users.usersimage._shared.domain.UserImage;
import es.grouppayments.backend.users.usersimage._shared.domain.UserImagesRepository;
import org.junit.Assert;

import java.util.Arrays;

import static org.junit.Assert.*;

public interface UsingUserImages {
    UserImagesRepository userImagesRepository();

    default void addUserImage(byte[] content){
        this.userImagesRepository().save(new UserImage(Arrays.hashCode(content), 1, content));
    }

    default void addUserImage(byte[] content, int count){
        this.userImagesRepository().save(new UserImage(Arrays.hashCode(content), count, content));
    }

    default void assertUserImageDeleted(int imageId){
        assertTrue(this.userImagesRepository().findById(imageId).isEmpty());
    }

    default void assertUserImageCount(int imageId, int expectedCount){
        assertEquals(this.userImagesRepository().findById(imageId).get().getCountOfUsersUsing(), expectedCount);
    }

    default void assertUserImageCreated(int imageId){
        assertTrue(this.userImagesRepository().findById(imageId).isPresent());
    }
}
