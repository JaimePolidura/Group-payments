import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationApiService} from "../../shared/auth/authentication-api.service";
import {LoginResponse} from "../../shared/auth/api/responses/login-response";
import {UserState} from "../../shared/users/model/user-state";
import {ServerNotificationsListener} from "../../shared/notificatinos/online/server-notifications-listener";
import {PushNotificationSchema} from "@capacitor/push-notifications";
import {PushNotificationService} from "../../shared/notificatinos/offline/push-notification.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  public isLoginButtonsClicked: boolean;

  constructor(
    private oauth: AuthenticationApiService,
    private router: Router,
    private actualRoute: ActivatedRoute,
    private socket: ServerNotificationsListener,
    private serverNotificationsConnection: ServerNotificationsListener,
    private pushNotifications: PushNotificationService,
  ){}

  ngOnInit(): void {
    this.isLoginButtonsClicked = false;
  }

  signInWithGoogle() {
    this.isLoginButtonsClicked = true;
    setTimeout(() => this.isLoginButtonsClicked = false, 5000);

    this.oauth.signInWithGoogle((loginResponse: LoginResponse) => {
      this.serverNotificationsConnection.connect();
      this.pushNotifications.registerPushNotifications();

      const alreadyRegistered: boolean = loginResponse.user.state == UserState.SIGNUP_ALL_COMPLETED;

      if(alreadyRegistered)
        this.router.navigate(["/main"]);
      else
        this.redirectToRegistration();
    }, () => {
      this.oauth.logout(() => this.socket.disconnect());
      window.alert("Currency not supported, impossible to login");

      this.router.navigate(["/"]);
    });
  }

  private redirectToRegistration(): void {
    this.router.navigate(["/register"]);
  }
}
