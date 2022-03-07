import {ServerEvent} from "./server-event";

export interface GroupMemberLeftEvent extends ServerEvent{
  userId: string;
  groupId: string;
}
