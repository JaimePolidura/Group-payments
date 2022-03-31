import {ServerEvent} from "./server-event";

export interface GroupMemberKicked extends ServerEvent{
  userId: string;
  groupId: string;
}
