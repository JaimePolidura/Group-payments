import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupMemberKicked extends ServerEvent{
  userId: string;
  groupId: string;
}
