import {ReplaySubject} from "rxjs";
import {ServerMessage} from "../server-message";

export abstract class ServerNotificationsListener {
  public abstract connect: () => void;
  public abstract disconnect: () => void;
  public abstract onNewNotification(event: unknown): void;
  public abstract getEventEmitter(): ReplaySubject<ServerMessage>
}
