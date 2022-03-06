import { Injectable } from '@angular/core';
import {Authentication} from "./authentication/authentication";
import {fetchEventSource} from "@microsoft/fetch-event-source";

@Injectable({
  providedIn: 'root'
})
export class ServerSentEventsService {
  private callbacks: {[name: string]: (msg: any) => void};

  constructor(private auth: Authentication){}

  public connect(): void {
    const onNewMessageFunction = (msg: any) => this.onNewMessage(msg);

    fetchEventSource('http://localhost:8080/sse', {
      headers: {'Authorization': `Bearer ${this.auth.getToken()}`},
      async onopen(res) { console.log("opened"); },
      onmessage(msg: any) { onNewMessageFunction(msg) }
    });
  }

  private onNewMessage(msg: any): void {
    console.log(msg);
  }
}
