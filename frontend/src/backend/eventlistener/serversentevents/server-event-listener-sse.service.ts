import { Injectable } from '@angular/core';
import {Authentication} from "../../users/authentication/authentication";
import {ServerEvent} from "../events/server-event";
import {ServerEventListener} from "../server-event-listener";
import {ReplaySubject} from "rxjs";
import {ServerMessage} from "../../server-message";
import {BackendUsingRoutesService} from "../../backend-using-routes.service";

@Injectable({
  providedIn: 'root'
})
export class ServerEventListenerSseService implements ServerEventListener{
  private readonly eventEmitter: ReplaySubject<ServerMessage>;
  private eventSource: EventSource;

  constructor(private auth: Authentication, private backendRoutes: BackendUsingRoutesService){
    this.eventEmitter = new ReplaySubject();
  }

  public connect(): void {
    this.eventSource = new EventSource(`${this.backendRoutes.USING}/eventstreaming/sse?token=${this.auth.getToken()}&userId=${this.auth.getUserId()}`);
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
    const eventServerMessage: ServerMessage = JSON.parse(messageEvent.data);

    console.log(eventServerMessage);

    this.eventEmitter.next(eventServerMessage);
  }

  getEventEmitter(): ReplaySubject<ServerMessage> {
    return this.eventEmitter;
  }
}
