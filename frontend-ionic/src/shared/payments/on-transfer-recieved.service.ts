import { Injectable } from '@angular/core';
import {TransferDone} from "./notifications/transfer-done";
import {ServerNotificationSubscriberService} from "../notificatinos/online/server-notification-subscriber.service";
import {AuthenticationCacheService} from "../auth/authentication-cache.service";
import {ToastService} from "../../app/_shared/toast/toast-service";

@Injectable({
  providedIn: 'root'
})
export class OnTransferRecievedService {

  constructor(private serverNotificationSubscriber: ServerNotificationSubscriberService,
              private auth: AuthenticationCacheService,
              private toast: ToastService) {

    this.serverNotificationSubscriber.subscribe<TransferDone>('transfer-done', e => {
      this.on(e);
    });
  }

  private on(transferDone: TransferDone): void {
    if(transferDone.to != this.auth.getUserId()) return;

    this.toast.success(
      `${transferDone.fromUsername} tranfered you ${transferDone.money} ${transferDone.currencyCode}`,
      `Description: ${transferDone.description}`
    );
  }
}
