package es.grouppayments.backend.groups.makepayment;

import es.grouppayments.backend._shared.domain.Utils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments._shared.domain.PaymentDone;
import es.grouppayments.backend.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments._shared.domain.UnprocessablePayment;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import es.jaime.javaddd.domain.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MakePaymentCommandHandler implements CommandHandler<MakePaymentCommand> {
    private final GroupService groupService;
    private final GroupMemberService groupMembers;
    private final PaymentMakerService paymentService;
    private final EventBus eventBus;

    @Override
    public void handle(MakePaymentCommand makePaymentCommand) {
        Group group = ensureGroupExistsAndGet(makePaymentCommand.getGruopId());
        this.ensureAdminOfGroup(group, makePaymentCommand.getUserId());
        List<GroupMember> groupMembersNotAdmin = ensureAtLeastOneMemberExceptAdminAndGet(group);
        double moneyToPayPerMember = group.getMoney() / groupMembersNotAdmin.size();

        ensureAllMembersCanPay(groupMembersNotAdmin, moneyToPayPerMember);

        for (GroupMember groupMember : groupMembersNotAdmin) {
            this.paymentService.makePayment(groupMember.getUserId(), group.getAdminUserId(), moneyToPayPerMember);
        }

        groupService.deleteById(makePaymentCommand.getGruopId());

        eventBus.publish(new PaymentDone(
                getUsersIdFromGroupMembers(groupMembersNotAdmin),
                group.getAdminUserId(),
                group.getDescription(),
                moneyToPayPerMember
        ));
    }

    private void ensureAllMembersCanPay(List<GroupMember> payerMembers, double money){
        Utils.allMatchOrThrow(payerMembers,
                member -> paymentService.isValid(member.getUserId(), money),
                UnprocessablePayment.of("Not enough balance"));
    }

    private void ensureAdminOfGroup(Group group, UUID userId){
        if(!group.getAdminUserId().equals(userId))
            throw new NotTheOwner("You are not the admin of the group");
    }

    private List<GroupMember> ensureAtLeastOneMemberExceptAdminAndGet(Group group){
        List<GroupMember> members = groupMembers.findMembersByGroupId(group.getGroupId());

        if(members.size() <= 1)
            throw new IllegalQuantity("The group should have least one member");

        return members.stream()
                .filter(notAdmin())
                .collect(Collectors.toList());
    }

    private Group ensureGroupExistsAndGet(UUID uuid){
        return this.groupService.findById(uuid)
                .orElseThrow(() -> new ResourceNotFound("Group not found"));
    }

    private Predicate<? super GroupMember> notAdmin(){
        return groupMember -> groupMember.getRole().equals(GroupMemberRole.USER);
    }

    private List<UUID> getUsersIdFromGroupMembers(List<GroupMember> groupMembers){
        return groupMembers.stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());
    }
}
