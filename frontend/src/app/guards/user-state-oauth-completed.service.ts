import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Authentication} from "../../backend/authentication/authentication";
import {UserState} from "../../model/user-state";

@Injectable({
  providedIn: 'root'
})
export class UserStateOAuthCompleted implements CanActivate {
  constructor(
    private authentication: Authentication,
    private router: Router,
  ) {}

  canActivate(
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const userStateOAuthCompleted = this.authentication.getUserState() == UserState.SIGNUP_OAUTH_COMPLETED;

    if(!userStateOAuthCompleted)
      this.router.navigate(["/register"]);

    return userStateOAuthCompleted;
  }
}
