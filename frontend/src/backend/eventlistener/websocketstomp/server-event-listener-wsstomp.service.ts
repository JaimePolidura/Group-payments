import { Injectable } from '@angular/core';
import {ServerEventListener} from "../server-event-listener";
import {ReplaySubject} from "rxjs";
import {BackendUsingRoutesService} from "../../backend-using-routes.service";
import {GroupStateService} from "../../../app/main/group-state.service";
import {Authentication} from "../../authentication/authentication";
import {ServerMessage} from "../../server-message";
import {AuthenticationSocketHeader} from "./authentication-socket-header";

declare var SockJS: any;
declare var Stomp: any;

@Injectable({
  providedIn: 'root'
})
export class ServerEventListenerWSStompService implements ServerEventListener{
  private stompClient: any;
  private readonly eventEmitter: ReplaySubject<ServerMessage>;

  constructor(private backendRoutes: BackendUsingRoutesService, private groupState: GroupStateService, private auth: Authentication,) {
    this.eventEmitter = new ReplaySubject<ServerMessage>();
  }

  connect(): void {
    const severUrl: string = `${this.backendRoutes.USING}/socket`;
    const serverSocketToSubscribe: string = `/group/${this.groupState.getCurrentGroup().groupId}`;
    const headers: AuthenticationSocketHeader = {token: this.auth.getToken(), userId: this.auth.getUserId(), groupId: this.groupState.getCurrentGroup().groupId};

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

  getEventEmitter(): ReplaySubject<ServerMessage> {
    return this.eventEmitter;
  }
}