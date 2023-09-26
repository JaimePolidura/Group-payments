import {UserState} from "../../../users/model/user-state";
import {Currency} from "../../../currencies/model/currency";
import {User} from "../../../users/model/user";

export interface LoginResponse {
  token: string,
  user: User,
}
