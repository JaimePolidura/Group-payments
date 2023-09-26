import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupPaymentDone extends ServerEvent{
  groupId: string,
  membersUsersId: string[],
  adminUserId: string,
  description: string,
  moneyPaidPerMember: number,
}
