import { Component, OnInit } from '@angular/core';
import {Authentication} from "../../backend/users/authentication/authentication";
import {ServerNotificationSubscriberService} from "../../backend/notificatinos/server-notification-subscriber.service";
import { ToastrService } from 'ngx-toastr';
import {TransferDone} from "../../backend/notificatinos/notifications/transfer-done";

@Component({
  selector: 'app-notifications-listener',
  templateUrl: './notifications-listener.component.html',
  styleUrls: ['./notifications-listener.component.css']
})
export class NotificationsListenerComponent implements OnInit {

  constructor(
    private auth: Authentication,
    private serverNotificationSubscriber: ServerNotificationSubscriberService,
    private toastPublisher: ToastrService
  ){}

  ngOnInit(): void {
    this.registerNotificationListener();
  }

  private registerNotificationListener(): void {
    this.onTransferRecieved();
  }

  private onTransferRecieved(): void {
    this.serverNotificationSubscriber.subscribe<TransferDone>('transfer-done', res => {
      if(res.to != this.auth.getUserId()) return;

      this.toastPublisher.success(
        `${res.fromUsername} tranfered you ${res.moneyUserToGotPaid} ${res.currencyCode}`,
        `${res.description}`,
        {
          progressBar: true
        }
      );
    });
  }
}
