package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.PaymentService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MakePaymentCommandHandler implements CommandHandler<MakePaymentCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMembers;
    private final PaymentService paymentService;

    @Override
    public void handle(MakePaymentCommand makePaymentCommand) {
        Group group = ensureGroupExistsAndGet(makePaymentCommand.getGruopId());

        List<UUID> membersUsersId = groupMembers.findMembersByGroupId(makePaymentCommand.getGruopId()).stream()
                .map(GroupMember::getUserId)
                .toList();
        double moneyToPayPerMember = group.getMoney() / membersUsersId.size();

        paymentService.makePayment(membersUsersId, moneyToPayPerMember);

        groupService.deleteById(makePaymentCommand.getGruopId());
    }

    private Group ensureGroupExistsAndGet(UUID uuid){
        return this.groupService.findById(uuid)
                .orElseThrow(() -> new ResourceNotFound("Group not found"));
    }
}
