package es.grouppayments.backend.users.usersimage.get;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetUserImageQuery implements Query {
    @Getter private final int imageId;
}
