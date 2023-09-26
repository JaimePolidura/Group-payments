package es.grouppayments.backend._shared.infrastructure.cqrs.command;

import es.grouppayments.backend._shared.infrastructure.cqrs.ApplicationCQRSClassMapper;
import es.jaime.javaddd.domain.cqrs.command.Command;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandHandlerMapper {
    private final Map<Class<Command>, CommandHandler> commandsHandlers;

    public CommandHandlerMapper(ApplicationCQRSClassMapper mapper) {
        this.commandsHandlers = mapper.find(CommandHandler.class);
    }

    public CommandHandler search(Class<? extends Command> commandClass) {
        return commandsHandlers.get(commandClass);
    }
}
