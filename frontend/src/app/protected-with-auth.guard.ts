import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Params, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
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
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const isLogged = this.isLogged();

    if(!isLogged)
      this.redirectToLogin(actualRoute);


    return isLogged;
  }

  private isLogged(): boolean {
    return this.authentication.isLogged();
  }

  private redirectToLogin(actualRoute: ActivatedRouteSnapshot): void {
    const groupId = actualRoute.paramMap.get("groupId");
    const urlFromShareGroup = groupId == null;

    urlFromShareGroup ?
      this.router.navigate(["/login"]) :
      this.router.navigate(["/login/groupToJoin/", groupId]);
  }

}
