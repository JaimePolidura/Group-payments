package es.grouppayments.backend.payments.stripe.makepayment;

import es.grouppayments.backend._shared.domain.Utils;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments._shared.domain.events.ErrorWhileMemberPaying;
import es.grouppayments.backend.payments._shared.domain.events.ErrorWhilePayingToAdmin;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static es.grouppayments.backend._shared.domain.Utils.*;

@Service
public class MakePaymentCommandHandler implements CommandHandler<MakePaymentCommand> {
    private final double fee;
    private final GroupService groupService;
    private final GroupMemberService groupMembers;
    private final PaymentMakerService paymentService;
    private final EventBus eventBus;

    public MakePaymentCommandHandler(GroupService groupService, GroupMemberService groupMembers, PaymentMakerService paymentService,
                                     EventBus eventBus, @Value("${grouppayments.fee}") double fee ) {
        this.groupService = groupService;
        this.groupMembers = groupMembers;
        this.paymentService = paymentService;
        this.eventBus = eventBus;
        this.fee = fee;
    }

    @Override
    public void handle(MakePaymentCommand makePaymentCommand) {
        Group group = this.ensureGroupExistsAndGet(makePaymentCommand.getGruopId());
        this.ensureGroupAbletToMakePayments(group);
        this.ensureAdminOfGroup(group, makePaymentCommand.getUserId());

        List<GroupMember> groupMembersWithoutAdmin = this.ensureAtLeastOneMemberExceptAdminAndGet(group);
        double moneyToPayPerMember = group.getMoney() / groupMembersWithoutAdmin.size();

        this.eventBus.publish(new PaymentInitialized(group.getGroupId()));

        double totalMoneyPaid = 0;

        for (GroupMember groupMember : groupMembersWithoutAdmin) {
            try{
                this.paymentService.paymentAppToAdmin(groupMember.getUserId(), moneyToPayPerMember);

                totalMoneyPaid += moneyToPayPerMember;
            }catch (Exception e){
                this.eventBus.publish(new ErrorWhileMemberPaying(group.getGroupId(), e.getMessage()));
            }
        }

        this.makePaymentAppToAdmin(group, deductFrom(totalMoneyPaid, fee));
    }

    private void makePaymentAppToAdmin(Group group, double totalMoney){
        try {
            this.paymentService.paymentAppToAdmin(group.getAdminUserId(), totalMoney);
        } catch (Exception e) {
            this.eventBus.publish(new ErrorWhilePayingToAdmin(group.getGroupId(), e.getMessage()));
        }
    }

    private void ensureGroupAbletToMakePayments(Group group){
        if(!group.canMakePayments())
            throw new IllegalState("Group is blocked to do payments");
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
        return this.groupService.findByIdOrThrowException(uuid);
    }

    private Predicate<? super GroupMember> notAdmin(){
        return groupMember -> groupMember.getRole().equals(GroupMemberRole.USER);
    }
}
