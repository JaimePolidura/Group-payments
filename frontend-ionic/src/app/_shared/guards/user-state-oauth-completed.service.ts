import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {UserState} from "../../../shared/users/model/user-state";
import {AuthenticationCacheService} from "../../../shared/auth/authentication-cache.service";

@Injectable({
  providedIn: 'root'
})
export class UserStateOAuthCompleted implements CanActivate {
  constructor(
    private auth: AuthenticationCacheService,
    private router: Router,
  ) {}

  canActivate(
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const allAuthCompleted = this.auth.getUserState() == UserState.SIGNUP_ALL_COMPLETED;
    const hasOAuthComplated = this.auth.getUserState() == UserState.SIGNUP_OAUTH_COMPLETED;
    const hasRegisredCreditCardDetails = this.auth.getUserState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED;

    if(allAuthCompleted)
      this.router.navigate(["/main"]);

    return hasOAuthComplated || hasRegisredCreditCardDetails;
  }
}
