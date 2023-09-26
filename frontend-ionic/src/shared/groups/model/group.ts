import {GroupState} from "./group-state";
import {Currency} from "../../currencies/model/currency";

export interface Group {
  groupId: string;
  description: string;
  createdDate: string;
  money: number;
  currency: Currency;
  adminUserId: string;
  state: GroupState;
}
