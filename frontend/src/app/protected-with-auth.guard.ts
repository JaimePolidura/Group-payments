import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {Authentication} from "../backend/authentication/authentication";

@Injectable({
  providedIn: 'root'
})
export class ProtectedWithAuthGuard implements CanActivate {
  constructor(
    private authentication: Authentication,
    private router: Router,
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.checkIfLoggedOrRedirect();
  }

  private checkIfLoggedOrRedirect(): boolean{
    if(!this.authentication.isLogged()){
      this.router.navigate( ["/login"]);
      return false;
    }else{
      return true;
    }
  }

}
