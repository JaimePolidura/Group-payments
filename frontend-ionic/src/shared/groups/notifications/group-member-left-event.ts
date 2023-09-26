import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupMemberLeftEvent extends ServerEvent{
  userId: string;
  groupId: string;
}
