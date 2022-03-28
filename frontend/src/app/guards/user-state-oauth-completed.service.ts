import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Observable} from 'rxjs';
import {Authentication} from "../../backend/users/authentication/authentication";
import {UserState} from "../../model/user/user-state";

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

    const allAuthCompleted = this.authentication.getUserState() == UserState.SIGNUP_ALL_COMPLETED;
    const hasOAuthComplated = this.authentication.getUserState() == UserState.SIGNUP_OAUTH_COMPLETED;
    const hasRegisredCreditCardDetails = this.authentication.getUserState() == UserState.SIGNUP_OAUTH_CREDIT_CARD_COMPLETED;

    console.log(allAuthCompleted);
    console.log(hasOAuthComplated);

    if(allAuthCompleted)
      this.router.navigate(["/main"]);

    return hasOAuthComplated || hasRegisredCreditCardDetails;
  }
}
