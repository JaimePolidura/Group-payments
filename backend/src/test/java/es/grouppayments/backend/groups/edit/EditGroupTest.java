package es.grouppayments.backend.groups.edit;

import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupState;
import es.jaime.javaddd.domain.exceptions.*;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class EditGroupTest extends EditGroupTestMother{

    @Test
    public void shouldEditGroupAndChange(){
        final double actualMoney = 10;
        final String newDescription = "new";
        final double newMoney = 20;
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId, actualMoney);

        execute(groupId, userId, newMoney, newDescription);

        assertEventRaised(GroupEdited.class);
        assertContentGroup(groupId, Group::getDescription, newDescription);
        assertContentGroup(groupId, Group::getMoney, newMoney);
    }

    @Test
    public void shouldntEditIllegalDesc(){
        assertThrows(IllegalLength.class, () -> {
            execute(UUID.randomUUID(), UUID.randomUUID(), 100, "");
        });

        assertThrows(IllegalLength.class, () -> {
            execute(UUID.randomUUID(), UUID.randomUUID(), 100, "piunwopqiynpeoquiyeboiuq");
        });
    }

    @Test
    public void shouldntEditIllegalMoneyNegative(){
        assertThrows(IllegalQuantity.class, () -> {
            execute(UUID.randomUUID(), UUID.randomUUID(), -1, "xd");
        });

        assertThrows(IllegalQuantity.class, () -> {
            execute(UUID.randomUUID(), UUID.randomUUID(), 0, "xd");
        });

        assertThrows(IllegalQuantity.class, () -> {
            execute(UUID.randomUUID(), UUID.randomUUID(), 1000000, "xd");
        });
    }

    @Test(expected = ResourceNotFound.class)
    public void shouldntEditGroupNotExists(){
        execute(UUID.randomUUID(), UUID.randomUUID(), 1000, "xd");
    }

    @Test(expected = NotTheOwner.class)
    public void shouldntEditGroupNotInGroup(){
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, UUID.randomUUID());

        execute(groupId, UUID.randomUUID(), 1000, "xd");
    }

    @Test(expected = IllegalState.class)
    public void shouldntEditInvalidState(){
        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        addGroup(groupId, userId, 10);
        changeStateTo(groupId, GroupState.PAYING);

        execute(groupId, userId, 11, "sa");
    }
}
