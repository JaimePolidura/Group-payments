package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUser;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.StripeUsersService;
import es.grouppayments.backend.users._shared.domain.User;
import es.grouppayments.backend.users._shared.domain.UserState;
import es.grouppayments.backend.users._shared.domain.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "grouppayments.db.seeder.use", havingValue = "true")
public class Seeder implements CommandLineRunner {
    private final UsersService usersService;
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final StripeUsersService stripeUsersService;
    private final boolean useAuthStripeAccounts;

    public Seeder(UsersService usersService, GroupService groupService, GroupMemberService groupMemberService, StripeUsersService stripeUsersService,
                  @Value("${grouppayments.db.seeder.stripeaccountswithauth}") boolean useAuthStripeAccounts) {
        this.usersService = usersService;
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
        this.stripeUsersService = stripeUsersService;
        this.useAuthStripeAccounts = useAuthStripeAccounts;
    }

    @Override
    public void run(String... args) throws Exception {
        usersService.update(new User(UUID.randomUUID(), "Jaime", "jaime.polidura@gmail.com", LocalDateTime.now(), "https://lh3.googleusercontent.com/a/AATXAJz7wrFagWv7s_MyF8vEv4ZNz72ciRuD3fk2i_lrsQ=s96-c", UserState.SIGNUP_ALL_COMPLETED));
        usersService.update(new User(UUID.randomUUID(), "JaimeTruman", "jaimetruman@gmail.com", LocalDateTime.now(), "https://lh3.googleusercontent.com/a/AATXAJz7wrFagWv7s_MyF8vEv4ZNz72ciRuD3fk2i_lrsQ=s96-c", UserState.SIGNUP_ALL_COMPLETED));
        usersService.update(new User(UUID.randomUUID(), "Jaime Polidura", "jaime.polidura@alumnos.uneatlantico.es", LocalDateTime.now(), "https://lh3.googleusercontent.com/a-/AOh14Gg2u6SQhIvGMiX1y5_8gPdDvswOr2O8INkFNVq4=s96-c", UserState.SIGNUP_ALL_COMPLETED));

        UUID groupId = UUID.randomUUID();
        groupService.create(groupId, "Group", 120, findUserIdByEmail("jaime.polidura@gmail.com"));
        groupMemberService.save(new GroupMember(findUserIdByEmail("jaimetruman@gmail.com"), groupId, GroupMemberRole.USER));
        groupMemberService.save(new GroupMember(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"), groupId, GroupMemberRole.USER));

        if(useAuthStripeAccounts){
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaime.polidura@gmail.com"),"pm_1KfmwgHd6M46OJ6ABTm1yDoG", "cus_LMVzN3wNMrP8gh", "acct_1Kfmx4Qk6j7fqwuo", true));
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaimetruman@gmail.com"),"pm_1Kfn1QHd6M46OJ6A5Gmqs3aP", "cus_LMW3VRhX2lAR2V", "acct_1Kfn1YQa0gzB5Dgo", true));
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"),"pm_1Kfn4LHd6M46OJ6AiOyGhSHv", "cus_LMW7L0ROrfUZJt", "acct_1Kfn4oQXFo41V5ba", true));
        }else{
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaime.polidura@gmail.com"),"pm_1Kfh2AHd6M46OJ6At3bP5ljx", "cus_LMPrk4ERbD3dNp", "acct_1Kfh2CQWCnLqz00C", true));
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaimetruman@gmail.com"),"pm_1Kfh4lHd6M46OJ6A4n0DZ5xN", "cus_LMPuzvpWBNNMJe", "acct_1Kfh4nQUa7cp5fFM", true));
            stripeUsersService.save(new StripeUser(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"),"pm_1Kfh6yHd6M46OJ6AjKt1RVNy", "cus_LMPwcLvWt9AtwD", "acct_1Kfh70QlGiEMtOxm", true));
        }
    }

    private UUID findUserIdByEmail(String email){
        return usersService.findByEmail(email)
                .get()
                .getUserId();
    }
}
