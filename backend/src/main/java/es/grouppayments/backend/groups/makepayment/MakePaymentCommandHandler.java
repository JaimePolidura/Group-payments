package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.PaymentDone;
import es.grouppayments.backend.payments.PaymentService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MakePaymentCommandHandler implements CommandHandler<MakePaymentCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMembers;
    private final PaymentService paymentService;
    private final EventBus eventBus;

    @Override
    public void handle(MakePaymentCommand makePaymentCommand) {
        Group group = ensureGroupExistsAndGet(makePaymentCommand.getGruopId());

        List<GroupMember> groupMembers = this.groupMembers.findMembersByGroupId(makePaymentCommand.getGruopId());
        double moneyToPayPerMember = group.getMoney() / groupMembers.size();

        for (GroupMember groupMember : groupMembers) {
            this.paymentService.makePayment(groupMember.getUserId(), moneyToPayPerMember);
        }

        groupService.deleteById(makePaymentCommand.getGruopId());

        eventBus.publish(new PaymentDone(
                getUsersIdFromGroupMembers(groupMembers),
                group.getAdminUserId(),
                group.getDescription(),
                moneyToPayPerMember
        ));
    }

    private Group ensureGroupExistsAndGet(UUID uuid){
        return this.groupService.findById(uuid)
                .orElseThrow(() -> new ResourceNotFound("Group not found"));
    }

    private List<UUID> getUsersIdFromGroupMembers(List<GroupMember> groupMembers){
        return groupMembers.stream()
                .filter(groupMember -> groupMember.getRole().equals(GroupMemberRole.USER))
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());
    }
}
