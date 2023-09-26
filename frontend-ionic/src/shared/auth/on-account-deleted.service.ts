import { Injectable } from '@angular/core';
import {ServerNotificationSubscriberService} from "../notificatinos/online/server-notification-subscriber.service";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class OnAccountDeletedService {

  constructor(
    private serverNotificationService: ServerNotificationSubscriberService,
    private router: Router,
  ) {

    this.serverNotificationService.subscribe('user-deleted', e => {
      this.on();
    });
  }

  private on() {
    this.router.navigateByUrl("login");
  }
}
