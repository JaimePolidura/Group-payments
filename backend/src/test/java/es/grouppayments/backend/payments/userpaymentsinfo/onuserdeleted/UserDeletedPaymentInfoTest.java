package es.grouppayments.backend.payments.userpaymentsinfo.onuserdeleted;

import es.grouppayments.backend.users.users._shared.domain.UserDeleted;
import org.junit.Test;

import java.util.UUID;

public final class UserDeletedPaymentInfoTest extends UserDeletedPaymentInfoTestMother{
    @Test
    public void shouldDelete(){
        UUID userId = UUID.randomUUID();
        addPaymentInfo(userId);

        on(new UserDeleted(userId));

        assertUserPaymentInfoDeleted(userId);
    }

    @Test
    public void shouldntDelete(){
        on(new UserDeleted(UUID.randomUUID()));
    }
}
