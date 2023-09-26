import {ServerEvent} from "../../notificatinos/server-event";

export interface InvitationRejected extends ServerEvent{
  invitationId: string,
  toUserId: string,
  fromUserId: string,
  groupId: string
}
