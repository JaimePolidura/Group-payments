import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Authentication} from "../../backend/users/authentication/authentication";
import {UserState} from "../../model/user/user-state";

@Injectable({
  providedIn: 'root'
})
export class UserStateSignUpAllCompleted implements CanActivate {
  constructor(
    private authentication: Authentication,
    private router: Router,
  ) {}

  canActivate(
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const signUpCompleted: boolean = this.authentication.getUserState() == UserState.SIGNUP_ALL_COMPLETED;

    if(!signUpCompleted)
      this.router.navigate(["/login"]);

    return signUpCompleted;
  }
}
