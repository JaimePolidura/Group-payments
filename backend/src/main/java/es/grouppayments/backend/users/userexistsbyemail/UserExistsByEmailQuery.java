package es.grouppayments.backend.users.userexistsbyemail;

import es.jaime.javaddd.domain.cqrs.query.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class UserExistsByEmailQuery implements Query {
    @Getter private final String email;
}
