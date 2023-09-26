package es.grouppayments.backend.users.users.edit;

import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.IllegalLength;
import es.jaime.javaddd.domain.exceptions.NotValid;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class EditUserCommandHandler implements CommandHandler<EditUserCommand> {
    private final UsersService usersService;
    private final UsersImagesService usersImagesService;

    @Override
    public void handle(EditUserCommand command) {
        this.ensureCurrencyNotNull(command);
        this.ensureCorrectCountry(command);
        this.ensureCorrectUsername(command);
        User userToEdit = this.ensureUsersExists(command.getUserId());

        User editedUser = new User(userToEdit.getUserId(), command.getUsername(), userToEdit.getEmail(), userToEdit.getLoggedDate(),
                userToEdit.getUserImageId(), userToEdit.getState(), command.getCountryCode(), command.getCurrency());

        this.usersService.update(editedUser);
    }

    private User ensureUsersExists(UUID userId){
        return this.usersService.getByUserId(userId);
    }

    private void ensureCorrectUsername(EditUserCommand command){
        if(command.getUsername() == null || command.getUsername().equals("") || command.getUsername().length() < 3
                || command.getUsername().length() >= 16){
            throw new IllegalLength("Illegal username format");
        }
    }

    private void ensureCurrencyNotNull(EditUserCommand command){
        if(command.getCurrency() == null){
            throw new ResourceNotFound("Currency cannot be null");
        }
    }

    private void ensureCorrectCountry(EditUserCommand command){
        if(command.getCountryCode() == null || command.getCountryCode().equals("") ||
                !command.getCurrency().usingCountries.contains(command.getCountryCode())){
            throw new NotValid("Not a valid country code");
        }
    }
}
