import {Group} from "../../../../model/group/group";
import {ServerEvent} from "../server-event";

export interface GroupEdited  extends ServerEvent{
  group: Group,
}
