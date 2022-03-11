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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainComponent,
    NavigationBarComponent,
    NonGroupOptionsComponent,
    GroupOptionsComponent,
    JoinGroupComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    SocialLoginModule,
    NgbModule,
  ],
  providers: [
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
