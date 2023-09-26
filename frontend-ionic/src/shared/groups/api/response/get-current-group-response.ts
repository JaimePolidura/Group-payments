import {Group} from "../../model/group";
import {User} from "../../../users/model/user";

export interface GetCurrentGroupResponse {
  group: Group;
  members: User[];
}
