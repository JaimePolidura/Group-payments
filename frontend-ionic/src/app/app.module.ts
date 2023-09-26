import {APP_INITIALIZER, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { MainComponent } from './main/main.component';
import { NavigationBarComponent } from './navigation-bar/navigation-bar.component';
import { NonGroupOptionsComponent } from './main/non-group-options/non-group-options.component';
import { GroupComponent } from './main/group-optins/group.component';
import {RegisterCardDetailsComponent} from "./regiser-card-details/register-card-details.component";
import {LoadingProgressBarComponent} from "./_shared/loading-progress-bar/loading-progress-bar.component";
import {PaymentsHistoryComponent} from "./main/non-group-options/payments-history/payments-history.component";
import {
  PaymentInfoModalComponent
} from "./main/non-group-options/payments-history/payment-info-modal/payment-info-modal.component";
import {MakeTransferModalComponent} from "./main/non-group-options/make-transfer-modal/make-transfer-modal.component";
import {JoinGroupModalComponent} from "./main/non-group-options/join-group-modal/join-group-modal.component";
import {CreateGroupModalComponent} from "./main/non-group-options/create-group-modal/create-group-modal.component";
import {InviteGroupComponent} from "./main/group-optins/group-options/invite-group/invite-group.component";
import {EditGroupComponent} from "./main/group-optins/group-options/edit-group/edit-group.component";
import {GroupPaymentComponent} from "./main/group-optins/group-payment/group-payment.component";
import {ConfirmPaymentComponent} from "./main/group-optins/confirm-payment/confirm-payment.component";
import {GroupMemberComponent} from "./main/group-optins/group-member/group-member.component";
import {GroupDescriptionComponent} from "./main/group-optins/group-description/group-description.component";
import {GroupOptionsComponent} from "./main/group-optins/group-options/group-options.component";
import {
  PendingInvitationComponent
} from "./main/non-group-options/join-group-modal/pending-invitation/pending-invitation.component";
import {AccountSettingsComponent} from "./main/non-group-options/account-settings/account-settings.component";
import {
  UserEditedModalComponent
} from "./main/non-group-options/account-settings/user-edited-modal/user-edited-modal.component";
import {
  DeleteAccountEmailSendedComponent
} from "./main/non-group-options/account-settings/delete-account-email-sended/delete-account-email-sended.component";
import {DeleteAccountConfirmComponent} from "./delete-account-confirm/delete-account-confirm.component";
import {
  DeleteAccountConfirmModalComponent
} from "./delete-account-confirm/delete-account-confirm-modal/delete-account-confirm-modal.component";
import {UserImageComponent} from "./_shared/user-image/user-image.component";
import {AutocompleteUserSearchComponent} from "./_shared/autocomplete-user-search/autocomplete-user-search.component";
import {NgxStripeModule} from "ngx-stripe";
import { ReactiveFormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from 'angularx-social-login';
import {AutocompleteLibModule} from "angular-ng-autocomplete";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatInputModule} from "@angular/material/input";
import {MatRippleModule} from "@angular/material/core";
import {ToastrModule} from "ngx-toastr";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  ServerNotificationsListenerSocketStompService
} from "../shared/notificatinos/online/server-notifications-listener-socket-stomp.service";
import {ServerNotificationsListener} from "../shared/notificatinos/online/server-notifications-listener";
import {ToastService} from "./_shared/toast/toast-service";
import {HttpRequestInterceptor} from "../shared/_shared/http-request-interceptor.service";
import {OAuthService} from "../shared/auth/oauth/oauth-service";
import {
  CapacitorGoogleOAuthServiceService
} from "../shared/auth/oauth/implementations/capacitor-google-oauth-service.service";
import {IonicToastService} from "./_shared/toast/implementations/ionic-toast.service";
import {OnTransferRecievedService} from "../shared/payments/on-transfer-recieved.service";
import {OnNewInvitationService} from "../shared/invitations/on-new-invitation.service";
import {OnInvitationRejectedService} from "../shared/invitations/on-invitation-rejected.service";
import {OnAccountDeletedService} from "../shared/auth/on-account-deleted.service";
import { SafeUrlPipePipe } from './_shared/pipes/safe-url-pipe.pipe';
import {ChangeCardComponent} from "./main/non-group-options/account-settings/change-card/change-card.component";
import {
  ChangeCardConfirmModalComponent
} from "./change-card-confirm/change-card-confirm-modal/change-card-confirm-modal.component";

const STRIPE_PUBLIC_KEY = "pk_test_51KdFRuHd6M46OJ6AWkTIoAh6RRcusDTqAEin4zxmvSkAEotVSwhmD6rD02ymmU3PJCAtylGS8ARrxejFlbscO8as00icxHNy4i";
const GOOGLE_CLIENT_ID = '135501655442-hmcu2927osmh0p6ahr18poeeacf4eu0v.apps.googleusercontent.com';
const ANGULARX_SOCIAL_CONFIG = {
  provide: 'SocialAuthServiceConfig',
  useValue: {
    autoLogin: false,
    providers: [{
      id: GoogleLoginProvider.PROVIDER_ID,
      provider: new GoogleLoginProvider(GOOGLE_CLIENT_ID)
    }]
  } as SocialAuthServiceConfig,
};
const SERVER_NOTIFICATIONS_LISTENERS: any[] = [
  OnTransferRecievedService, OnNewInvitationService,
  OnInvitationRejectedService, OnAccountDeletedService
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    NavigationBarComponent,
    NonGroupOptionsComponent,
    GroupComponent,
    RegisterCardDetailsComponent,
    LoadingProgressBarComponent,
    PaymentsHistoryComponent,
    PaymentInfoModalComponent,
    MakeTransferModalComponent,
    JoinGroupModalComponent,
    CreateGroupModalComponent,
    InviteGroupComponent,
    EditGroupComponent,
    GroupPaymentComponent,
    ConfirmPaymentComponent,
    GroupMemberComponent,
    GroupDescriptionComponent,
    GroupOptionsComponent,
    PendingInvitationComponent,
    AccountSettingsComponent,
    UserEditedModalComponent,
    DeleteAccountEmailSendedComponent,
    DeleteAccountConfirmComponent,
    DeleteAccountConfirmModalComponent,
    UserImageComponent,
    AutocompleteUserSearchComponent,
    SafeUrlPipePipe,
    ChangeCardComponent,
    ChangeCardConfirmModalComponent
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    NgxStripeModule.forRoot(STRIPE_PUBLIC_KEY),
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SocialLoginModule,
    NgbModule,
    AutocompleteLibModule,
    MatProgressBarModule,
    MatInputModule,
    MatRippleModule,
    ToastrModule.forRoot({
      timeOut: 8000,
      preventDuplicates: true,
    }),
  ],
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    { provide: ServerNotificationsListener, useExisting: ServerNotificationsListenerSocketStompService },
    { provide: ToastService, useExisting: IonicToastService },
    { provide: OAuthService, useExisting: CapacitorGoogleOAuthServiceService },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },
    ANGULARX_SOCIAL_CONFIG,
    {
      provide: APP_INITIALIZER,
      useFactory: () => () => {},
      deps: [...SERVER_NOTIFICATIONS_LISTENERS],
      multi: true
    }
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
