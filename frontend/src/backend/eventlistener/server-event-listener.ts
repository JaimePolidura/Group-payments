import {ReplaySubject} from "rxjs";
import {ServerMessage} from "../server-message";

export abstract class ServerEventListener {
  public abstract connect: () => void;
  public abstract disconnect: () => void;
  public abstract onNewEvent(event: unknown): void;
  public abstract getEventEmitter(): ReplaySubject<ServerMessage>
}
