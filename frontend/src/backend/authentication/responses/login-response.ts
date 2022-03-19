import {UserState} from "../../../model/user-state";

export interface LoginResponse {
  userId: string,
  token: string,
  userState: UserState,
}
