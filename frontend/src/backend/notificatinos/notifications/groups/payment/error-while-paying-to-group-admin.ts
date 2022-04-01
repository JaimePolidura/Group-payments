import {ServerEvent} from "../../server-event";

export interface ErrorWhilePayingToGroupAdmin extends ServerEvent{
  groupId: string,
  errorMessage: string,
  userId: string,
}
