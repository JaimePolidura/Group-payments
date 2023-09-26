import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthenticationApiService} from "../../../shared/auth/authentication-api.service";
import {AuthenticationCacheService} from "../../../shared/auth/authentication-cache.service";

@Injectable({
  providedIn: 'root'
})
export class NeedsToBeAuthenticated implements CanActivate {
  constructor(
    private authentication: AuthenticationApiService,
    private router: Router,
    private auth: AuthenticationCacheService,
    ) {}

  canActivate(
    actualRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    const isLogged = this.isLogged();

    if(!isLogged)
      this.router.navigate(["/login"]);

    return isLogged;
  }

  private isLogged(): boolean {
    return this.auth.isLogged();
  }
}
