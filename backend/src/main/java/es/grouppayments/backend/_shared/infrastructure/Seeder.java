package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.users._shared.domain.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@Component
@ConditionalOnProperty(value = "useseeder", havingValue = "true")
public class Seeder implements CommandLineRunner {
    private final UsersService usersService;
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;

    @Override
    public void run(String... args) throws Exception {
        usersService.save("Jaime", "jaime.polidura@gmail.com", "https://lh3.googleusercontent.com/a/AATXAJz7wrFagWv7s_MyF8vEv4ZNz72ciRuD3fk2i_lrsQ=s96-c");
        usersService.save("Jorge", "jorge.polidura@gmail.com", "https://lh3.googleusercontent.com/a-/AOh14Gg2u6SQhIvGMiX1y5_8gPdDvswOr2O8INkFNVq4=s96-c");
        usersService.save("Julia", "julia.polidura@gmail.com", "https://lh3.googleusercontent.com/a/AATXAJyfnpTxyjxSddAWxFVyOH6kk2I6JXrb4N509rhxcA=s96-c");

        UUID groupId = UUID.randomUUID();
        groupService.create(groupId, "Group", 120, findUserIdByEmail("jaime.polidura@gmail.com"));

        groupMemberService.save(new GroupMember(findUserIdByEmail("jorge.polidura@gmail.com"), groupId, GroupMemberRole.USER));
        groupMemberService.save(new GroupMember(findUserIdByEmail("julia.polidura@gmail.com"), groupId, GroupMemberRole.USER));
    }

    private UUID findUserIdByEmail(String email){
        return usersService.findByEmail(email)
                .get()
                .getUserId();
    }
}
