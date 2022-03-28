import {UserState} from "../../../../model/user/user-state";

export interface LoginResponse {
  userId: string,
  token: string,
  userState: UserState,
  countryCode: string,
  email: string,
}
