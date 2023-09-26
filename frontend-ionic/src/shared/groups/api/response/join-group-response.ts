import {Group} from "../../model/group";
import {User} from "../../../users/model/user";

export interface JoinGroupResponse {
  group: Group;
  members: User[]
}
