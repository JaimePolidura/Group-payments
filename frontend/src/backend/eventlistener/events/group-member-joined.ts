import {ServerEvent} from "./server-event";

export interface GroupMemberJoined extends ServerEvent{
  userId: string,
  groupId: string,
}
