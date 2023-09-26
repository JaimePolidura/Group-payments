import {Component, NgZone} from '@angular/core';
import {Router} from "@angular/router";
import {CurrenciesService} from "../shared/currencies/currencies.service";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  constructor(private router: Router, private zone: NgZone, private currecies: CurrenciesService) {
  }
}
