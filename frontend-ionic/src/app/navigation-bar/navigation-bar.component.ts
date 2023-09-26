import { Component, OnInit } from '@angular/core';
import {AuthenticationApiService} from "../../shared/auth/authentication-api.service";
import {Router} from "@angular/router";
import {AuthenticationCacheService} from "../../shared/auth/authentication-cache.service";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  constructor(
    private authCache: AuthenticationCacheService,
    private authApi: AuthenticationApiService,
    private router: Router,
  ){}

  ngOnInit(): void {
  }

  isLogged(): boolean {
    return this.authCache.isLogged();
  }

  name(): string{
    return this.authCache.getUsername();
  }

  logout(): void {
    this.authApi.logout(() => {
      this.router.navigate(["/login"]);
    });
  }

}
