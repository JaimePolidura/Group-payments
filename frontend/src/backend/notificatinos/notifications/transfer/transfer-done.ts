import {ServerEvent} from "../server-event";

export interface TransferDone extends ServerEvent {
  from: string,
  to: string,
  fromUsername: string,
  money: number,
  currencyCode: string,
  description: string,
}
