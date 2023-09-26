import {ServerEvent} from "../../notificatinos/server-event";

export interface ErrorWhileMakingTransfer extends ServerEvent {
  from: string,
  errorCause: string
}
