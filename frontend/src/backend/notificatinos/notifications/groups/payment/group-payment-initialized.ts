import {ServerEvent} from "../../server-event";

export interface GroupPaymentInitialized extends ServerEvent{
  groupId: string,
}
