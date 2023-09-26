package es.grouppayments.backend.users.usersimage._shared.application;

import es.grouppayments.backend.users.usersimage._shared.domain.UserImage;
import es.grouppayments.backend.users.usersimage._shared.domain.UserImagesRepository;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class UsersImagesService {
    //5 MB
    public static final int MIN_SIZE = 5;

    private final UserImagesRepository repository;

    public void save(UserImage userImage){
        this.repository.save(userImage);
    }

    public int save(byte[] imageContent){
        int imageIdHash = Arrays.hashCode(imageContent);
        var optionalImage = this.repository.findById(imageIdHash);

        if(optionalImage.isPresent())
            this.save(optionalImage.get().incrementCountByOne());
        else
            this.save(new UserImage(imageIdHash, 1, imageContent));

        return imageIdHash;
    }

    public void decrementCountByOneOrDelete(int imageId){
        var userImage = this.repository.findById(imageId).get()
                .decrementCountByOne();

        if(userImage.getCountOfUsersUsing() <= 0)
            this.deleteById(imageId);
        else
            this.save(userImage);
    }

    public boolean existsById(int imageId){
        return this.repository.findById(imageId).isPresent();
    }

    public Optional<UserImage> findById(int imageId){
        return this.repository.findById(imageId);
    }

    public UserImage getById(int imageId){
        return this.repository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFound("Image no found for that id"));
    }

    public void deleteById(int userImageId){
        this.repository.deleteByImageId(userImageId);
    }
}
