import { Injectable } from '@angular/core';
import {Authentication} from "../../authentication/authentication";
import {ServerEvent} from "../events/server-event";
import {ServerEventListener} from "../server-event-listener";
import {ReplaySubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ServerEventListenerSseService implements ServerEventListener{
  private readonly eventEmitter: ReplaySubject<{ name: string; body: ServerEvent }>;
  private eventSource: EventSource;

  constructor(private auth: Authentication){
    this.eventEmitter = new ReplaySubject();
  }

  public connect(): void {
    this.eventSource = new EventSource(`http://localhost:8080/sse?token=${this.auth.getToken()}&userId=${this.auth.getUserId()}`);
    this.eventSource.onopen = () => console.log("opened");
    this.eventSource.onmessage = (msg): void => {
      this.onNewEvent(msg);
    }
  }

  public disconnect(): void{
    this.eventSource.close();
  }

  onNewEvent(messageEvent: unknown): void {
    // @ts-ignore
    const event: any = JSON.parse(messageEvent.data);

    console.log(event);

    this.eventEmitter.next(event);
  }

  getEventEmitter(): ReplaySubject<{ name: string; body: ServerEvent }> {
    return this.eventEmitter;
  }
}
