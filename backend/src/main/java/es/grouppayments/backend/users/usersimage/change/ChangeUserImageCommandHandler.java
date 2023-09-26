package es.grouppayments.backend.users.usersimage.change;

import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;
import es.grouppayments.backend.users.usersimage.processor.MainImageProcessor;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.AlreadyExists;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@AllArgsConstructor
@Service
public final class ChangeUserImageCommandHandler implements CommandHandler<ChangeUserImageCommand> {
    private final UsersImagesService usersImagesService;
    private final UsersService usersService;
    private final MainImageProcessor imageProcessorService;

    @Override
    public void handle(ChangeUserImageCommand command) {
        var user = this.usersService.getByUserId(command.getUserId());
        int oldUserImageId = user.getUserImageId();
        byte[] newImageContentResized = imageProcessorService.apply(command.getImageContent());
        int newUserImageId = Arrays.hashCode(newImageContentResized);
        this.ensureNotSameImage(oldUserImageId, newUserImageId);
        this.ensureNotMaxSize(newImageContentResized);

        this.usersImagesService.save(newImageContentResized);
        this.usersImagesService.decrementCountByOneOrDelete(oldUserImageId);
        this.usersService.update(user.withUserImageId(newUserImageId));
    }

    private void ensureNotMaxSize(byte[] bytes){
        int totalBytes = bytes.length;
        int fiveMegasInBytes = 5242880;

        if(totalBytes / fiveMegasInBytes > UsersImagesService.MIN_SIZE)
            throw new IllegalQuantity("Max size per image 5MB");
    }

    private void ensureNotSameImage(int oldUserImageId, int newUserImageId){
        if(oldUserImageId == newUserImageId)
            throw new AlreadyExists("You have already that image");
    }
}
