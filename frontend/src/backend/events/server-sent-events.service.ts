import { Injectable } from '@angular/core';
import {Authentication} from "../authentication/authentication";
import {fetchEventSource} from "@microsoft/fetch-event-source";
import {ServerEvent} from "./model/server-event";

@Injectable({
  providedIn: 'root'
})
export class ServerSentEventsService {
  private readonly callbacks: {[name: string]: ((msg: ServerEvent) => void)};
  private eventSource: EventSource;

  constructor(private auth: Authentication){
    this.callbacks = {};
  }

  public connect(): void {
    this.eventSource = new EventSource(`http://localhost:8080/sse?token=${this.auth.getToken()}&userId=${this.auth.getUserId()}`);
    this.eventSource.onopen = () => console.log("opened");
    this.eventSource.onmessage = (msg): void => this.onNewMessage(msg);
  }

  private onNewMessage(msg: MessageEvent): void {
    const data: any = JSON.parse(msg.data);

    console.log(data);

    this.callbacks[data.name](data.body);
  }

  public subscribe(eventName: string, callback: (msg: ServerEvent) => void): void {
    this.callbacks[eventName] = callback;
  }

  public disconnect(): void{
    this.eventSource.close();
  }
}
