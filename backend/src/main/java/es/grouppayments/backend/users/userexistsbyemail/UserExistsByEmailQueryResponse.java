package es.grouppayments.backend.users.userexistsbyemail;

import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class UserExistsByEmailQueryResponse implements QueryResponse {
    @Getter private final boolean exists;
}
