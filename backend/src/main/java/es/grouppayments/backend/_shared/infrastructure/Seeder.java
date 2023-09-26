package es.grouppayments.backend._shared.infrastructure;

import es.grouppayments.backend.groupmembers._shared.domain.GroupMember;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberRole;
import es.grouppayments.backend.groupmembers._shared.domain.GroupMemberService;
import es.grouppayments.backend.groups._shared.domain.GroupService;
import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationInfo;
import es.grouppayments.backend.notifications.offline._shared.domain.UserOfflineNotificationRepository;
import es.grouppayments.backend.payments.paymentshistory._shared.domain.*;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.domain.PaymentUserInfo;
import es.grouppayments.backend.payments.userpaymentsinfo._shared.application.PaymentUsersInfoService;
import es.grouppayments.backend.users.users._shared.domain.User;
import es.grouppayments.backend.users.users._shared.domain.UserState;
import es.grouppayments.backend.users.users._shared.application.UsersService;
import es.grouppayments.backend.users.usersimage._shared.application.UsersImagesService;
import es.grouppayments.backend.users.usersimage._shared.domain.UserImage;
import es.jaime.javaddd.domain.Utils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static es.grouppayments.backend.users.users._shared.domain.Currency.EUR;

@Component
@ConditionalOnProperty(value = "grouppayments.db.seeder.use", havingValue = "true")
public class Seeder implements CommandLineRunner {
    private static final UUID jaimePoliduraUserId = UUID.fromString("278ad3c5-f488-4121-92b7-d0c5a19b669c");
    private static final UUID jaimeTrumanUserId = UUID.fromString("823871c8-2934-4177-ba6f-bd1d25743b75");
    private static final UUID poliduraJaimeUserId = UUID.fromString("0a153970-bbaa-4a6c-953a-fe2f273a2d8d");
    private static final UUID jaimePoliduraUneatUserId = UUID.fromString("5323670a-1856-45c9-b64d-759515207eb3");

    private final UsersService usersService;
    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final UsersImagesService usersImagesService;
    private final PaymentUsersInfoService stripeUsersService;
    private final PaymentsHistoryRepository paymentsHistoryRepository;
    private final UserOfflineNotificationRepository offilneNotifications;
    private final boolean useAuthStripeAccounts;

    public Seeder(UsersService usersService, GroupService groupService, GroupMemberService groupMemberService, UsersImagesService usersImagesService, PaymentUsersInfoService stripeUsersService,
                  PaymentsHistoryRepository paymentsHistoryRepository, UserOfflineNotificationRepository repository, @Value("${grouppayments.db.seeder.stripeaccountswithauth}") boolean useAuthStripeAccounts) {
        this.usersService = usersService;
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
        this.usersImagesService = usersImagesService;
        this.stripeUsersService = stripeUsersService;
        this.paymentsHistoryRepository = paymentsHistoryRepository;
        this.offilneNotifications = repository;
        this.useAuthStripeAccounts = useAuthStripeAccounts;
    }

    @Override
    public void run(String... args) throws Exception {
        byte[] image1 = this.fetchImage("https://lh3.googleusercontent.com/a/AATXAJz7wrFagWv7s_MyF8vEv4ZNz72ciRuD3fk2i_lrsQ=s96-c");
        byte[] image2 = this.fetchImage("https://lh3.googleusercontent.com/a-/AOh14Gg2u6SQhIvGMiX1y5_8gPdDvswOr2O8INkFNVq4=s96-c");

        this.usersImagesService.save(new UserImage(Arrays.hashCode(image1), 3, image1));
        this.usersImagesService.save(new UserImage(Arrays.hashCode(image2), 1, image2));

        usersService.update(new User(jaimePoliduraUserId, "Jaime", "jaime.polidura@gmail.com", LocalDateTime.now(), Arrays.hashCode(image1), UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));
        usersService.update(new User(jaimeTrumanUserId, "JaimeTruman", "jaimetruman@gmail.com", LocalDateTime.now(), Arrays.hashCode(image1), UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));
        usersService.update(new User(jaimePoliduraUneatUserId, "Jaime Polidura", "jaime.polidura@alumnos.uneatlantico.es", LocalDateTime.now(), Arrays.hashCode(image2), UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));
        usersService.update(new User(poliduraJaimeUserId, "Polidura Jaime", "polidurajaime@gmail.com", LocalDateTime.now(), Arrays.hashCode(image1), UserState.SIGNUP_ALL_COMPLETED, "ES", EUR));

        UUID groupId = UUID.randomUUID();
        groupService.create(groupId, "Group", 120, findUserIdByEmail("jaime.polidura@gmail.com"), EUR);
        groupMemberService.save(new GroupMember(findUserIdByEmail("jaimetruman@gmail.com"), groupId, GroupMemberRole.USER));
        groupMemberService.save(new GroupMember(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"), groupId, GroupMemberRole.USER));

        if(useAuthStripeAccounts){
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaime.polidura@gmail.com"),"pm_1KfmwgHd6M46OJ6ABTm1yDoG", "cus_LMVzN3wNMrP8gh", "acct_1Kfmx4Qk6j7fqwuo", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaimetruman@gmail.com"),"pm_1Kfn1QHd6M46OJ6A5Gmqs3aP", "cus_LMW3VRhX2lAR2V", "acct_1Kfn1YQa0gzB5Dgo", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"),"pm_1Kfn4LHd6M46OJ6AiOyGhSHv", "cus_LMW7L0ROrfUZJt", "acct_1Kfn4oQXFo41V5ba", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("polidurajaime@gmail.com"),"pm_1KkWWuHd6M46OJ6APwQ8OXof", "cus_LRPL3cuqbQDcqt", "acct_1KkWWwQWxdk8siGx", true));
        }else{
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaime.polidura@gmail.com"),"pm_1Kfh2AHd6M46OJ6At3bP5ljx", "cus_LMPrk4ERbD3dNp", "acct_1Kfh2CQWCnLqz00C", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaimetruman@gmail.com"),"pm_1Kfh4lHd6M46OJ6A4n0DZ5xN", "cus_LMPuzvpWBNNMJe", "acct_1Kfh4nQUa7cp5fFM", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"),"pm_1Kfh6yHd6M46OJ6AjKt1RVNy", "cus_LMPwcLvWt9AtwD", "acct_1Kfh70QlGiEMtOxm", true));
            stripeUsersService.save(new PaymentUserInfo(findUserIdByEmail("polidurajaime@gmail.com"),"pm_1KkWWuHd6M46OJ6APwQ8OXof", "cus_LRPL3cuqbQDcqt", "acct_1KkWWwQWxdk8siGx", true));
        }

        this.offilneNotifications.save(new UserOfflineNotificationInfo(jaimePoliduraUserId, "drhbWK8eRwq1GEYwZb5HLH:APA91bH-FON0Ej46DT1C747MqkFeNkPvADKYj83EU07PsvIDyxDknG_t2Ne6GC1QmfspzLnmgfXxqVRVNk0j7bfZXe-_xGZDshwF54K8YBLW9VsNTDx1pQH4FWHSjwWxnmv2i0edFKoq"));
        this.offilneNotifications.save(new UserOfflineNotificationInfo(jaimeTrumanUserId, "dqRW7yreQ_eRe28ch4AjCB:APA91bFl6-bCCMuoTukH00blcyFUpR4SqYrmgw1sp4V3jscRJeCAwAfsI4WRJEbgoY0HrNxl-huz9cdOzK5k1wWrtE3tX-fg2FXorU-JQW8OHpANdiOwJZ6XKSSjdwFmh3DdHaAzv7jj"));
        this.offilneNotifications.save(new UserOfflineNotificationInfo(poliduraJaimeUserId, "dqRW7yreQ_eRe28ch4AjCB:APA91bFl6-bCCMuoTukH00blcyFUpR4SqYrmgw1sp4V3jscRJeCAwAfsI4WRJEbgoY0HrNxl-huz9cdOzK5k1wWrtE3tX-fg2FXorU-JQW8OHpANdiOwJZ6XKSSjdwFmh3DdHaAzv7jj"));
        this.offilneNotifications.save(new UserOfflineNotificationInfo(jaimePoliduraUneatUserId, "dqRW7yreQ_eRe28ch4AjCB:APA91bFl6-bCCMuoTukH00blcyFUpR4SqYrmgw1sp4V3jscRJeCAwAfsI4WRJEbgoY0HrNxl-huz9cdOzK5k1wWrtE3tX-fg2FXorU-JQW8OHpANdiOwJZ6XKSSjdwFmh3DdHaAzv7jj"));


        this.addRandomPayments(findUserIdByEmail("jaime.polidura@gmail.com"), 20);
        this.addRandomPayments(findUserIdByEmail("jaimetruman@gmail.com"), 20);
        this.addRandomPayments(findUserIdByEmail("jaime.polidura@alumnos.uneatlantico.es"), 20);
    }

    private void addRandomPayments(UUID userId, int number){
        Utils.repeat(number, () -> addRandomPayment(userId));
    }

    private void addRandomPayment(UUID userId){
        PaymentContext paymentType = Math.random() < 0.5 ? PaymentContext.TRANSFERENCE : PaymentContext.GROUP_PAYMENT;
        boolean isPaid = Math.random() < 0.5;
        boolean isUserIdAdmin = Math.random() < 0.5;
        boolean isError = Math.random() < 0.3;
        UUID otherUserId = findUserIdByEmail("polidurajaime@gmail.com");

        this.paymentsHistoryRepository.save(new Payment(
                UUID.randomUUID(),
                isPaid ? otherUserId : userId,
                isPaid ? userId : otherUserId,
                Math.random() * 100,
                EUR,
                LocalDateTime.now(),
                "Payment",
                isError ? PaymentState.ERROR : PaymentState.SUCCESS,
                paymentType,
                isError ? "Not enough balance" : null
        ));
    }

    private UUID findUserIdByEmail(String email){
        return usersService.findByEmail(email)
                .get()
                .getUserId();
    }

    @SneakyThrows
    private byte[] fetchImage(String url){
        InputStream inputStream = new URL(url).openStream();
        byte[] imageBytes = inputStream.readAllBytes();
        int imageIdHash = Arrays.hashCode(imageBytes);
        inputStream.close();

        return imageBytes;
    }
}
