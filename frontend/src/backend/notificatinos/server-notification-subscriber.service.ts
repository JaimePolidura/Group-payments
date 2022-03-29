import { Injectable } from '@angular/core';
import {ServerEvent} from "./notifications/server-event";
import {ServerNotificationsListener} from "./server-notifications-listener";

@Injectable({
  providedIn: 'root'
})
export class ServerNotificationSubscriberService {
  protected readonly callbacks: {[name: string]: ((event: ServerEvent) => void)};

  constructor(private serverEventListener: ServerNotificationsListener) {
    this.callbacks = {};
    this.serverEventListener.getEventEmitter().subscribe(event => {
      const callbak = this.callbacks[event.name];

      if(callbak != undefined)
        callbak(event.body);
    });
  }

  public subscribe<T extends ServerEvent>(eventName: string, callback: (event: T) => void): void {
    this.callbacks[eventName] = <(event: ServerEvent) => void> callback;
  }
}
