import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Authentication} from "../../backend/authentication/authentication";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(
    private router: Router,
    private auth: Authentication,
  ) { }

  ngOnInit(): void {
    if(!this.auth.isLogged()){
      this.router.navigate(["/"]);
    }
  }

}
