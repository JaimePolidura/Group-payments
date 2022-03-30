import {ServerEvent} from "./server-event";

export interface TransferDone extends ServerEvent{
  from: string,
  fromUsername: string,
  to: string,
  moneyUserFromPaid: number,
  moneyUserToGotPaid: number
  currencyCode: string,
  description: string
}
