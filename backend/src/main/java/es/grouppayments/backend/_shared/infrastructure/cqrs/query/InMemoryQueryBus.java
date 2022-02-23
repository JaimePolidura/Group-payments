package es.grouppayments.backend._shared.infrastructure.cqrs.query;

import es.jaime.javaddd.domain.cqrs.query.Query;
import es.jaime.javaddd.domain.cqrs.query.QueryBus;
import es.jaime.javaddd.domain.cqrs.query.QueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryQueryBus implements QueryBus {
    private final QueryHandlerMapper queryHandlerMapper;

    @Override
    public <R extends QueryResponse> R ask(Query query) {
        return (R) this.queryHandlerMapper.search(query.getClass()).handle(query);
    }
}
