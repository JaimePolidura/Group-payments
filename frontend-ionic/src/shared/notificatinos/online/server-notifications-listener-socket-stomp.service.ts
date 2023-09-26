import { Injectable } from '@angular/core';
import {ServerNotificationsListener} from "./server-notifications-listener";
import {ReplaySubject} from "rxjs";
import {BackendUsingRoutesService} from "../../_shared/backend-using-routes.service";
import {GroupCacheService} from "../../groups/group-cache.service";
import {ServerMessage} from "../../_shared/server-message";
import {AuthenticationSocketHeader} from "./authentication-socket-header";
import {AuthenticationCacheService} from "../../auth/authentication-cache.service";

declare var SockJS: any;
declare var Stomp: any;

@Injectable({
  providedIn: 'root'
})
export class ServerNotificationsListenerSocketStompService implements ServerNotificationsListener{
  private stompClient: any;
  private readonly eventEmitter: ReplaySubject<ServerMessage>;

  constructor(private backendRoutes: BackendUsingRoutesService,
              private groupState: GroupCacheService,
              private auth: AuthenticationCacheService,) {
    this.eventEmitter = new ReplaySubject<ServerMessage>();
  }

  connect(): void {
    const severUrl: string = `${this.backendRoutes.USING}/notifications/online/socket`;
    const serverSocketToSubscribe: string = `/user/${this.auth.getUserId()}`;
    const headers: AuthenticationSocketHeader = {token: this.auth.getToken(), userId: this.auth.getUserId()};

    const ws = new SockJS(severUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    this.stompClient.connect(headers, function(frame: any) {
      console.log("connected");

      that.stompClient.subscribe(serverSocketToSubscribe, (message: any) => {
        that.onNewNotification(message);
      });
    });
  }

  disconnect(): void {
    this.stompClient.disconnect();
  }

  onNewNotification(eventRawData: unknown): void {
    // @ts-ignore
    console.log(JSON.parse(eventRawData.body))
    // @ts-ignore
    this.eventEmitter.next(JSON.parse(eventRawData.body));
  }

  getEventEmitter(): ReplaySubject<ServerMessage> {
    return this.eventEmitter;
  }
}
