package es.grouppayments.backend.users.usersimage._shared.domain;

import java.util.Optional;

public interface UserImagesRepository {
    void save(UserImage userImage);

    Optional<UserImage> findById(int imageId);

    void deleteByImageId(int imageId);
}
