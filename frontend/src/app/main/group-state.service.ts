import { Injectable } from '@angular/core';
import {Group} from "../../model/group";
import {User} from "../../model/user";

@Injectable({
  providedIn: 'root'
})
export class GroupStateService {
  private currentGroup: Group;
  private currentGroupMembers: User[];

  constructor() { }

  public getCurrentGroup(): Group {
    return this.currentGroup;
  }

  public getCurrentGroupMembers(): User[]{
    return this.currentGroupMembers;
  }

  public setCurrentGroupMembers(members: User[]): void {
    this.currentGroupMembers = members;
  }

  public setCurrentGroup(group: Group): void {
    this.currentGroup = group;
  }

  public addMember(member: User): void {
    this.currentGroupMembers.push(member);
  }

  public isAdminOfCurrentGroup(userId: string): boolean {
    return this.currentGroup != undefined && this.currentGroup.adminUserId == userId;
  }

  public isInAGroup(): boolean {
    return this.currentGroup != undefined;
  }

  public clearState(): void {
    // @ts-ignore
    this.currentGroup = undefined;
    // @ts-ignore
    this.currentGroupMembers = undefined;
  }
}
