import { Injectable } from '@angular/core';
import {Authentication} from "../authentication/authentication";
import {ServerEvent} from "./model/server-event";

@Injectable({
  providedIn: 'root'
})
export class ServerSentEventsService {
  private readonly callbacks: {[name: string]: ((event: ServerEvent) => void)};
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

    this.callbacks[data.name](data.body);
  }

  public subscribe<T extends ServerEvent>(eventName: string, callback: (event: T) => void): void {
    this.callbacks[eventName] = <(event: ServerEvent) => void> callback;
  }

  public disconnect(): void{
    this.eventSource.close();
  }
}
