package es.grouppayments.backend._shared.infrastructure.cqrs.command;

import es.jaime.javaddd.domain.cqrs.command.Command;
import es.jaime.javaddd.domain.cqrs.command.CommandBus;
import es.jaime.javaddd.domain.database.TransactionManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class InMemoryCommandBus implements CommandBus {
    private final CommandHandlerMapper handlerMapper;
    private final TransactionManager transactionManager;

    @Override
    public void dispatch(Command command) {
        try {
            transactionManager.start();
            handlerMapper.search(command.getClass()).handle(command);
            transactionManager.commit();
        }catch (Exception ignored){
            transactionManager.rollback();
        }
    }
}
