package es.grouppayments.backend.users.users.edit;

import es.grouppayments.backend.users.users._shared.domain.Currency;
import es.jaime.javaddd.domain.exceptions.IllegalLength;
import es.jaime.javaddd.domain.exceptions.NotValid;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public final class EditUserTest extends EditUserTestMother{
    @Test
    public void shouldPartiallyEditUser(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        final Currency newCurrency = Currency.USD;
        final String newCountryCode = "US";

        execute(new EditUserCommand(userId, DEFAULT_USERNAME, newCurrency, newCountryCode));

        assertContentUser(userId, user -> user.getUsername().equalsIgnoreCase(DEFAULT_USERNAME));
        assertContentUser(userId, user -> user.getUserImageId() == Arrays.hashCode(new byte[0]));
        assertContentUser(userId, user -> user.getCurrency() == newCurrency);
        assertContentUser(userId, user -> user.getCountry().equalsIgnoreCase(newCountryCode));
    }

    @Test
    public void shouldFullyEditUser(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        final String newUsername = "jaime";
        final Currency newCurrency = Currency.USD;
        final String newCountryCode = "US";

        execute(new EditUserCommand(userId, newUsername, newCurrency, newCountryCode));

        assertContentUser(userId, user -> user.getUsername().equalsIgnoreCase(newUsername));
        assertContentUser(userId, user -> user.getUserImageId() == Arrays.hashCode(new byte[0]));
        assertContentUser(userId, user -> user.getCurrency() == newCurrency);
        assertContentUser(userId, user -> user.getCountry().equalsIgnoreCase(newCountryCode));
    }

    @Test(expected = ResourceNotFound.class)
    public void invalidCurrency(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        execute(userId, "paco", null, "ES");
    }

    @Test
    public void invalidUsername(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        assertThrows(IllegalLength.class, () -> this.execute(userId, null, Currency.EUR, "ES"));
        assertThrows(IllegalLength.class, () -> this.execute(userId, "", Currency.EUR, "ES"));
        assertThrows(IllegalLength.class, () -> this.execute(userId, "ho", Currency.EUR, "ES"));
        assertThrows(IllegalLength.class, () -> this.execute(userId, "tengomasdedieciseisdigitosxd", Currency.EUR, "ES"));
    }

    @Test(expected = ResourceNotFound.class)
    public void userNotExists(){
        execute(new EditUserCommand(UUID.randomUUID(), "lkas", Currency.EUR, "ES"));
    }

    @Test
    public void invalidCountry(){
        UUID userId = UUID.randomUUID();
        addUser(userId);

        assertThrows(NotValid.class, () -> this.execute(userId, "juan", Currency.EUR, null));
        assertThrows(NotValid.class, () -> this.execute(userId, "juan", Currency.EUR, ""));
        assertThrows(NotValid.class, () -> this.execute(userId, "juan", Currency.EUR, "GER"));
    }
}
