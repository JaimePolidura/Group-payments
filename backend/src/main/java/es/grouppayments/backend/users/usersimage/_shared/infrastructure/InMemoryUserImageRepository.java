package es.grouppayments.backend.users.usersimage._shared.infrastructure;

import es.grouppayments.backend.users.usersimage._shared.domain.UserImage;
import es.grouppayments.backend.users.usersimage._shared.domain.UserImagesRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public final class InMemoryUserImageRepository implements UserImagesRepository {
    private final Map<Integer ,UserImage> userImages;

    public InMemoryUserImageRepository(){
        this.userImages = new ConcurrentHashMap<>();
    }

    @Override
    public void save(UserImage userImage) {
        this.userImages.put(userImage.getImageId(), userImage);
    }

    @Override
    public Optional<UserImage> findById(int imageId) {
        return Optional.ofNullable(this.userImages.get(imageId));
    }

    @Override
    public void deleteByImageId(int imageId) {
        this.userImages.remove(imageId);
    }
}
