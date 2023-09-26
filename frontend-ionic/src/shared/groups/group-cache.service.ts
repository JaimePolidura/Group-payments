import {Injectable} from '@angular/core';
import {Group} from "./model/group";
import {User} from "../users/model/user";
import {GroupState} from "./model/group-state";
import {Currency} from "../currencies/model/currency";

@Injectable({
  providedIn: 'root'
})
export class GroupCacheService {
  private group: Group;
  private members: User[];

  constructor(){
    this.members = [];
  }

  public getGroup(): Group {
    return this.group;
  }

  public getCurrency(): Currency {
    return this.group.currency;
  }

  public getMembers(): User[]{
    return this.members;
  }

  public isGroupInPayingState(): boolean {
    return this.group.state == GroupState.PAYING;
  }

  public setGroupState(newGroupState: GroupState){
    this.group = {
      ...this.group,
      state: newGroupState,
    }
  }

  public setMembers(members: User[]): void {
    this.members = members;
  }

  public setGroup(group: Group): void {
    this.group = group;
  }

  public addMember(member: User): void {
    if (!this.existsMemberByUserId(member.userId)) {
      this.members = [...this.members.concat([member])];
    }
  }

  private existsMemberByUserId(userId: string): boolean{
    return this.members.filter(member => member.userId == userId).length != 0;
  }

  public isAdminOfCurrentGroup(userId: string): boolean {
    return this.group != undefined && this.group.adminUserId == userId;
  }

  public isNotEmpty(): boolean {
    return this.group != undefined;
  }

  public getMemberByUserId(userId: string): User{
    return this.members.filter(groupMember => groupMember.userId == userId)[0];
  }

  public deleteGroupMemberById(userId: string): User | undefined {
    let index = this.members.findIndex(member => member.userId == userId);
    let userToRemove: User | undefined = index >= 0 ? {...this.members[index]} : undefined;

    if(index == -1) return undefined;

    this.members.splice(index, 1);

    return userToRemove;
  }

  public existsMemberByEmail(email: string): boolean {
    return this.members.filter(user => user.email == email)
      .length > 0;
  }

  public calculateTotalMoneyPerMember(): number {
    const notOnlyAdminInGroup: boolean = this.members.length - 1 > 0;

    return notOnlyAdminInGroup ?
      this.group.money / (this.members.length - 1) :
      0 ;
  }

  public clear(): void {
    // @ts-ignore
    this.group = undefined;
    this.members = [];
  }
}
