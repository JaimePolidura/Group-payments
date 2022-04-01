import {ServerEvent} from "../server-event";

export interface ErrorWhileMakingTransfer extends ServerEvent {
  from: string,
  errorCause: string
}
