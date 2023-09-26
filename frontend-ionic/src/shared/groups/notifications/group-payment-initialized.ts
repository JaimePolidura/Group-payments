import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupPaymentInitialized extends ServerEvent{
  groupId: string,
}
