import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Authentication} from "../../backend/users/authentication/authentication";
import {LoginResponse} from "../../backend/users/authentication/responses/login-response";
import {UserState} from "../../model/user/user-state";
import {ServerEventListener} from "../../backend/eventlistener/server-event-listener";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private oauth: Authentication,
    private router: Router,
    private actualRoute: ActivatedRoute,
    private socket: ServerEventListener,
  ){}

  ngOnInit(): void {
  }

  signInWithGoogle() {
    this.oauth.signInWithGoogle((loginResponse: LoginResponse) => {
      const alreadyRegistered: boolean = loginResponse.userState == UserState.SIGNUP_ALL_COMPLETED;

      if(alreadyRegistered)
        this.redirectToMain();
      else
        this.redirectToRegistration();
    }, () => {
      this.oauth.logout(() => this.socket.disconnect());
      window.alert("Currency not supported, impossible to login");

      this.router.navigate(["/"]);
    });
  }

  private redirectToMain(): void {
    const hasUrlToJoinGroup: boolean = this.hasUrlToJoinGroup();

    hasUrlToJoinGroup ?
      this.router.navigate(["/join", this.getGroupIdToJoin()]) :
      this.router.navigate(["/main"]);
  }

  private redirectToRegistration(): void {
    this.router.navigate(["/register"]);
  }

  private hasUrlToJoinGroup(): boolean {
    return this.actualRoute.snapshot.params["groupId"] != undefined;
  }

  private getGroupIdToJoin(): string {
    return this.actualRoute.snapshot.params["groupId"];
  }
}
