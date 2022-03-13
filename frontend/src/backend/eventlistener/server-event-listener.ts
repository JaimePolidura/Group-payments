import {ReplaySubject} from "rxjs";
import {ServerEvent} from "./events/server-event";

export abstract class ServerEventListener {
  public abstract connect: () => void;
  public abstract disconnect: () => void;
  public abstract onNewEvent(event: unknown): void;
  public abstract getEventEmitter(): ReplaySubject<{name: string, body: ServerEvent}>
}
