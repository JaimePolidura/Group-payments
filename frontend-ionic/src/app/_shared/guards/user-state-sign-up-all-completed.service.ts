import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {UserState} from "../../../shared/users/model/user-state";
import {AuthenticationCacheService} from "../../../shared/auth/authentication-cache.service";

@Injectable({
  providedIn: 'root'
})
export class UserStateSignUpAllCompleted implements CanActivate {
  constructor(
    private auth: AuthenticationCacheService,
    private router: Router,
  ) {}

  canActivate(
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const signUpCompleted: boolean = this.auth.getUserState() == UserState.SIGNUP_ALL_COMPLETED;

    if(!signUpCompleted)
      this.router.navigate(["/login"]);

    return signUpCompleted;
  }
}
