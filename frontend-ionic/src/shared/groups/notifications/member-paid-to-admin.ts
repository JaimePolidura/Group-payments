import {ServerEvent} from "../../notificatinos/server-event";

export interface MemberPaidToAdmin extends ServerEvent {
  groupId: string,
  adminUserId: string,
  money: number,
  userId: string,
}
