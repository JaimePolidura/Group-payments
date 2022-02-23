package es.grouppayments.backend._shared.infrastructure;

import es.jaime.javaddd.domain.database.TransactionManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeTransactionManager implements TransactionManager {
    @Override
    public void start() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }
}
