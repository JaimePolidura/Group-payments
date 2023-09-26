package es.grouppayments.backend.users.usersimage._shared.domain;

import es.jaime.javaddd.domain.Aggregate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class UserImage extends Aggregate {
    @Getter private final int imageId; //Hash
    @Getter private final int countOfUsersUsing;
    @Getter private final byte[] content;

    public UserImage incrementCountByOne(){
        return new UserImage(imageId, countOfUsersUsing + 1, content);
    }

    public UserImage decrementCountByOne(){
        return new UserImage(imageId, countOfUsersUsing - 1, content);
    }
}
