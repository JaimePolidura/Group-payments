package es.grouppayments.backend.groups.payment;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.Group;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.currencies._shared.domain.CurrencyService;
import es.grouppayments.backend.payments.payments._shared.domain.ComimssionPolicy;
import es.grouppayments.backend.payments.payments._shared.domain.PaymentMakerService;
import es.grouppayments.backend.payments.payments._shared.domain.events.*;
import es.grouppayments.backend.users._shared.domain.UsersService;
import es.jaime.javaddd.domain.cqrs.command.CommandHandler;
import es.jaime.javaddd.domain.event.EventBus;
import es.jaime.javaddd.domain.exceptions.IllegalQuantity;
import es.jaime.javaddd.domain.exceptions.IllegalState;
import es.jaime.javaddd.domain.exceptions.NotTheOwner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupPaymentCommandHandler implements CommandHandler<GroupPaymentCommand> {
    private final ComimssionPolicy commissionPolicy;
    private final GroupService groupService;
    private final GroupMemberService groupMembers;
    private final PaymentMakerService paymentService;
    private final EventBus eventBus;
    private final UsersService usersService;
    private final CurrencyService currencyService;

    @Override
    public void handle(GroupPaymentCommand command) {
        Group group = this.ensureGroupExistsAndGet(command.getGruopId());
        this.ensureGroupAbleToMakePayments(group);
        this.ensureAdminOfGroup(group, command.getUserId());

        List<GroupMember> groupMembersWithoutAdmin = this.ensureAtLeastOneMemberExceptAdminAndGet(group);
        double moneyToPayPerMember = group.getMoney() / groupMembersWithoutAdmin.size();
        String currencyCodeForPayment = this.getCurrencyCodeForUser(group.getAdminUserId());

        this.eventBus.publish(new GroupPaymentInitialized(group.getGroupId()));

        double totalMoneyPaid = 0;

        for (GroupMember groupMember : groupMembersWithoutAdmin) {
            try{
                this.paymentService.paymentUserToApp(groupMember.getUserId(), moneyToPayPerMember, currencyCodeForPayment);

                totalMoneyPaid += moneyToPayPerMember;

                this.eventBus.publish(new GroupMemberPayingAppDone(groupMember.getUserId(), moneyToPayPerMember, group));
            }catch (Exception e){
                this.eventBus.publish(new ErrorWhileGroupMemberPaying(group, e.getMessage(), groupMember.getUserId()));
            }
        }

        this.makePaymentAppToAdmin(group, this.commissionPolicy.deductCommission(totalMoneyPaid), currencyCodeForPayment);

        this.eventBus.publish(new GroupPaymentDone(group.getGroupId(), groupMembersWithoutAdmin.stream().map(GroupMember::getUserId).toList(),
                group.getAdminUserId(), group.getDescription(), moneyToPayPerMember));
    }

    private void makePaymentAppToAdmin(Group group, double totalMoney, String currencyCode){
        try {
            this.paymentService.paymentAppToUser(group.getAdminUserId(), totalMoney, currencyCode);

            this.eventBus.publish(new AppPayingGroupAdminDone(totalMoney, group));
        } catch (Exception e) {
            this.eventBus.publish(new ErrorWhilePayingToGroupAdmin(group, e.getMessage(), group.getAdminUserId(), totalMoney));
        }
    }

    private String getCurrencyCodeForUser(UUID userId){
        String countryCode = this.usersService.getByUserId(userId)
                .getCountry();

        return this.currencyService.getByCountryCode(countryCode)
                .getCode();
    }

    private void ensureGroupAbleToMakePayments(Group group){
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
                .filter(hasRoleOfUser())
                .collect(Collectors.toList());
    }

    private Group ensureGroupExistsAndGet(UUID uuid){
        return this.groupService.findByIdOrThrowException(uuid);
    }

    private Predicate<? super GroupMember> hasRoleOfUser(){
        return groupMember -> groupMember.getRole().equals(GroupMemberRole.USER);
    }
}
