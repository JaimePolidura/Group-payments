import { Injectable } from '@angular/core';
import {ServerEvent} from "./events/server-event";
import {ServerEventListener} from "./server-event-listener";

@Injectable({
  providedIn: 'root'
})
export class ServerEventsSubscriberService {
  protected readonly callbacks: {[name: string]: ((event: ServerEvent) => void)};

  constructor(private serverEventListener: ServerEventListener,) {
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
