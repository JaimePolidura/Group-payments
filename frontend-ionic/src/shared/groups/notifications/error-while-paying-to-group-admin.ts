import {ServerEvent} from "../../notificatinos/server-event";

export interface ErrorWhilePayingToGroupAdmin extends ServerEvent{
  groupId: string,
  errorMessage: string,
  userId: string,
}
