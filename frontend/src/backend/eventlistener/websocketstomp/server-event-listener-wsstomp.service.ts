import { Injectable } from '@angular/core';
import {ServerEventListener} from "../server-event-listener";
import {ReplaySubject} from "rxjs";
import {ServerEvent} from "../events/server-event";
import {BackendUsingRoutesService} from "../../backend-using-routes.service";
import {GroupStateService} from "../../../app/main/group-state.service";
import {Authentication} from "../../authentication/authentication";

declare var SockJS: any;
declare var Stomp: any;

@Injectable({
  providedIn: 'root'
})
export class ServerEventListenerWSStompService implements ServerEventListener{
  private stompClient: any;
  private readonly eventEmitter: ReplaySubject<{ name: string; body: ServerEvent }>;

  constructor(private backendRoutes: BackendUsingRoutesService, private groupState: GroupStateService, private auth: Authentication,) {
    this.eventEmitter = new ReplaySubject<{name: string; body: ServerEvent}>();
  }

  connect(): void {
    const severUrl: string = `${this.backendRoutes.USING}/socket`;
    const serverSocketToSubscribe: string = `/group/${this.groupState.getCurrentGroup().groupId}`;
    const headers = {token: this.auth.getToken(), userId: this.auth.getUserId(), groupId: this.groupState.getCurrentGroup().groupId};

    const ws = new SockJS(severUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect(headers, function(frame: any) {
      console.log("connected");

      that.stompClient.subscribe(serverSocketToSubscribe, (message: any) => {
        that.onNewEvent(message);
      });
    });
  }

  disconnect(): void {
    this.stompClient.disconnect();
  }

  onNewEvent(eventRawData: unknown): void {
    // @ts-ignore
    this.eventEmitter.next(JSON.parse(eventRawData.body));
  }

  getEventEmitter(): ReplaySubject<{ name: string; body: ServerEvent }> {
    return this.eventEmitter;
  }
}
