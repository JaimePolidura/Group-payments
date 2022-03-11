import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Authentication} from "../../backend/authentication/authentication";

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
  ){}

  ngOnInit(): void {
  }

  signInWithGoogle() {
    this.oauth.signInWithGoogle(() => {
      this.redirectToMain();
    });
  }

  private redirectToMain(): void {
    const hasUrlToJoinGroup: boolean = this.hasUrlToJoinGroup();

    hasUrlToJoinGroup ?
      this.router.navigate(["/join", this.getGroupIdToJoin()]) :
      this.router.navigate(["/main"]);

  }

  private hasUrlToJoinGroup(): boolean {
    return this.actualRoute.snapshot.params["groupId"] != undefined;
  }

  private getGroupIdToJoin(): string {
    return this.actualRoute.snapshot.params["groupId"];
  }
}
