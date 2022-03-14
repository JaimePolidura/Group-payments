import {GroupState} from "./group-state";

export interface Group {
  groupId: string;
  description: string;
  createdDate: string;
  money: number;
  adminUserId: string;
  state: GroupState;
}
