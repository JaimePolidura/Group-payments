import {ServerEvent} from "../../server-event";

export interface MemberPaidToAdmin extends ServerEvent {
  groupId: string,
  adminUserId: string,
  money: number,
  userId: string,
}
