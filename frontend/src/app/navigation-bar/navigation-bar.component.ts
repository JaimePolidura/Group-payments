import { Component, OnInit } from '@angular/core';
import {Authentication} from "../../backend/authentication/authentication";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  constructor(
    private auth: Authentication,
    private router: Router,
  ){}

  ngOnInit(): void {
  }

  isLogged(): boolean {
    return this.auth.isLogged();
  }

  name(): string{
    return this.auth.getName();
  }

  photoURL(): string{
    return this.auth.getPhotoURL();
  }

  logout(): void {
    this.auth.logout(() => {
      this.router.navigate(["/login"]);
    });
  }

}
