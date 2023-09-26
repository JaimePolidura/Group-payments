import { Injectable } from '@angular/core';
import {Capacitor} from "@capacitor/core";
import {ActionPerformed, PushNotifications, PushNotificationSchema, Token} from "@capacitor/push-notifications";
import {UserOfflineNotificationInfoApiService} from "./user-offline-notification-info-api.service";

@Injectable({
  providedIn: 'root'
})
export class PushNotificationService {

  constructor(private userOfflineNotificationInfoSerivce: UserOfflineNotificationInfoApiService) {
  }

  public registerPushNotifications() {
    if(Capacitor.platform === 'web') return;

    PushNotifications.requestPermissions().then(result => {
      if (result.receive === 'granted')
        PushNotifications.register();
    });

    // On success, we should be able to receive notifications
    PushNotifications.addListener('registration', (token: Token) => {
      this.userOfflineNotificationInfoSerivce.register({
        token: token.value
      }).subscribe(res => {
        console.log("subscribed");
      });
      }
    );

    // Method called when tapping on a notification
    PushNotifications.addListener('pushNotificationActionPerformed', (notification: ActionPerformed) => {
      window.alert(notification.notification.data);
    });
  }
}
