import {Group} from "../model/group";
import {ServerEvent} from "../../notificatinos/server-event";

export interface GroupEdited  extends ServerEvent{
  group: Group,
}
