import { User } from "src/shared/users/model/user";
import {Group} from "../../model/group";

export interface CreateGroupResponse {
  group: Group,
  members: User[],
}
