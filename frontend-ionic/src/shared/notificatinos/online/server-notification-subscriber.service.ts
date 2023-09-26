import { Injectable } from '@angular/core';
import {ServerEvent} from "../server-event";
import {ServerNotificationsListener} from "./server-notifications-listener";

@Injectable({
  providedIn: 'root'
})
export class ServerNotificationSubscriberService {
  protected readonly callbacks: {[name: string]: ((event: ServerEvent) => void)[]};

  constructor(private serverEventListener: ServerNotificationsListener) {
    this.callbacks = {};
    this.serverEventListener.getEventEmitter().subscribe(event => {
      const callbacks = this.callbacks[event.name];

      if(callbacks != undefined && callbacks.length > 0)
        callbacks.forEach(callback => callback(event.body));
    });
  }

  public subscribe<T extends ServerEvent>(eventName: string, callback: (event: T) => void): void {
    const callbacks = this.callbacks[eventName];

    if(callbacks == undefined || callbacks.length == 0)
      this.callbacks[eventName] = [<(event: ServerEvent) => void> callback];
    else
      this.callbacks[eventName] = callbacks.concat([<(event: ServerEvent) => void> callback]);
  }
}
