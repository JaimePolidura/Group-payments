package es.grouppayments.backend.users.usersimage.change;

import es.jaime.javaddd.domain.exceptions.AlreadyExists;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

public final class ChangeUserImageTest extends ChangeUserImageTestMother{
    @Test
    public void shouldChangeAndIncreaseCount(){
        byte[] newUserImageContent = new byte[]{1, 1};
        final int initialNewImageCount = 1;
        int newUserImageId = Arrays.hashCode(newUserImageContent);

        UUID userId = UUID.randomUUID();
        addUser(userId);
        addUserImage(new byte[0]);
        addUserImage(newUserImageContent);

        this.handle(new ChangeUserImageCommand(userId,newUserImageContent));

        assertUserImageDeleted(DEFAULT_IMAGE_ID);
        assertUserImageCreated(newUserImageId);
        assertContentUser(userId, u -> u.getUserImageId() == newUserImageId);
        assertUserImageCount(newUserImageId, initialNewImageCount + 1);
    }

    @Test
    public void shouldChangeAndDecreaseCountOldImage(){
        UUID userId = UUID.randomUUID();
        addUser(userId);
        final int initialCount = 2;
        addUserImage(new byte[0], initialCount);

        byte[] newUserImageContent =  new byte[]{1, 1};
        int newUserImageId = Arrays.hashCode(newUserImageContent);

        this.handle(new ChangeUserImageCommand(userId,newUserImageContent));

        assertUserImageCount(DEFAULT_IMAGE_ID, initialCount -1);
        assertUserImageCreated(newUserImageId);
        assertContentUser(userId, u -> u.getUserImageId() == newUserImageId);
    }

    @Test
    public void shouldChangeAndDeleteOldImage(){
        UUID userId = UUID.randomUUID();
        addUser(userId);
        addUserImage(new byte[0]);

        byte[] newUserImageContent =  new byte[]{1, 1};
        int newUserImageId = Arrays.hashCode(newUserImageContent);

        this.handle(new ChangeUserImageCommand(userId,newUserImageContent));

        assertUserImageDeleted(DEFAULT_IMAGE_ID);
        assertUserImageCreated(newUserImageId);
        assertContentUser(userId, u -> u.getUserImageId() == newUserImageId);
    }

    @Test(expected = IllegalQuantity.class)
    public void imageToLarge(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        this.handle(new ChangeUserImageCommand(userId, new byte[100000000]));
    }

    //new byte[0] hascode -> 0 same as default userimage id
    @Test(expected = AlreadyExists.class)
    public void notSameImage(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        this.handle(new ChangeUserImageCommand(userId, new byte[0]));
    }

}
