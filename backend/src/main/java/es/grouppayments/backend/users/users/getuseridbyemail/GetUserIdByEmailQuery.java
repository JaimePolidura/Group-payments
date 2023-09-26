package es.grouppayments.backend.users.users.getuseridbyemail;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class GetUserIdByEmailQuery implements Query {
    @Getter private final String email;
}
