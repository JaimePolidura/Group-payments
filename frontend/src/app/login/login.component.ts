import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Authentication} from "../../services/authentication";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(
    private oauth: Authentication,
    private router: Router
  ){}

  ngOnInit(): void {
    this.oauth.subscribeToAuthState(() => {
      this.redirectToMain();
    });
  }

  signInWithGoogle() {
    this.oauth.signInWithGoogle(() => {
      this.redirectToMain();
    });
  }

  private redirectToMain(): void {
    this.router.navigate(["/main"]);
  }
}
