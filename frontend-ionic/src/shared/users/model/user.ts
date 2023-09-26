import {UserState} from "./user-state";
import {Currency} from "../../currencies/model/currency";

export interface User {
  userId: string,
  username: string,
  email: string,
  userImageId: number,
  currency: Currency,
  state: UserState,
  country: string
}
