import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ReactiveFormsModule} from "@angular/forms";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from "./login/login.component";
import {MainComponent} from "./main/main.component";
import {NavigationBarComponent} from "./navigation-bar/navigation-bar.component";
import {GoogleLoginProvider, SocialAuthServiceConfig, SocialLoginModule} from "angularx-social-login";
import {HttpRequestInterceptor} from "../backend/http-request-interceptor.service";
import { GroupOptionsComponent } from './main/group-optins/group-options.component';
import {NonGroupOptionsComponent} from "./main/non-group-options/non-group-options.component";
import { JoinGroupComponent } from './join-group/join-group.component';
import {ServerNotificationsListener} from "../backend/notificatinos/server-notifications-listener";
import {
  ServerNotificationsListenerSocketStompService
} from "../backend/notificatinos/websocketstomp/server-notifications-listener-socket-stomp.service";
import { RegisterCardDetailsComponent } from './regiser-card-details/register-card-details.component';
import {NgxStripeModule} from "ngx-stripe";
import { LoadingProgressBarComponent } from './loading-progress-bar/loading-progress-bar.component';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatInputModule} from "@angular/material/input";
import {PaymentsHistoryComponent} from "./main/non-group-options/payments-history/payments-history.component";
import { PaymentInfoModalComponent } from './main/non-group-options/payments-history/payment-info-modal/payment-info-modal.component';
import { MakeTransferModalComponent } from './main/non-group-options/make-transfer-modal/make-transfer-modal.component';
import { ToastrModule } from 'ngx-toastr';
import { NotificationsListenerComponent } from './notifications-listener/notifications-listener.component';

const STRIPE_PUBLIC_KEY = "pk_test_51KdFRuHd6M46OJ6AWkTIoAh6RRcusDTqAEin4zxmvSkAEotVSwhmD6rD02ymmU3PJCAtylGS8ARrxejFlbscO8as00icxHNy4i";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    NavigationBarComponent,
    NonGroupOptionsComponent,
    GroupOptionsComponent,
    JoinGroupComponent,
    RegisterCardDetailsComponent,
    LoadingProgressBarComponent,
    PaymentsHistoryComponent,
    PaymentInfoModalComponent,
    MakeTransferModalComponent,
    NotificationsListenerComponent,
  ],
  imports: [
    BrowserModule,
    NgxStripeModule.forRoot(STRIPE_PUBLIC_KEY),
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SocialLoginModule,
    NgbModule,
    MatProgressBarModule,
    MatInputModule,
    ToastrModule.forRoot({
      timeOut: 10000,
      preventDuplicates: true,
    }),
  ],
  providers: [
    { provide: ServerNotificationsListener, useExisting: ServerNotificationsListenerSocketStompService },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [{
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider('322555566439-jua66a4h8rlmn8q1jogrp918a5giqaf9.apps.googleusercontent.com')
        }]
      } as SocialAuthServiceConfig,
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
