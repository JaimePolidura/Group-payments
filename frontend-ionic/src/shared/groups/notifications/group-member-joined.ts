import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupMemberJoined extends ServerEvent{
  userId: string,
  groupId: string,
}
