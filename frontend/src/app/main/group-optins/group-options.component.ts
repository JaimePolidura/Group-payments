import { Component, OnInit } from '@angular/core';
import {GroupStateService} from "../group-state.service";
import {Group} from "../../../model/group";
import {User} from "../../../model/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {KickGroupMemberRequest} from "../../../backend/groups/request/kick-group-member-request";
import {Authentication} from "../../../backend/authentication/authentication";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {

  constructor(
    public groupState: GroupStateService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private auth: Authentication,
    ) { }

  ngOnInit(): void {
  }

  public leaveGroup() {
    this.groupsApi.leaveGroup({groupId: this.currentGroup().groupId, ignoreThis: ""}).subscribe(res => {
      this.groupState.clearState();
    });
  }

  public currentGroup(): Group {
    return this.groupState.getCurrentGroup();
  }

  public currentGroupMembers(): User[] {
    return this.groupState.getCurrentGroupMembers();
  }

  public copyToClipboard(toCopy: any) {
    navigator.clipboard.writeText(toCopy);
    this.closeModal();
  }

  private closeModal(): void {
    this.modalService.dismissAll();
  }

  public kickGroupMember(userId: string): void {
    const request: KickGroupMemberRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      userIdToKick: userId
    };

    // @ts-ignore
    const userDeleted: User = this.groupState.deleteGroupMemberById(userId);

    this.groupsApi.kickGroupMember(request).subscribe(res => {}, err => {
      this.groupState.addMember(userDeleted);
    });
  }

  isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }

  isAdminOfCurrentGroup(userId: string): boolean{
    return this.groupState.isAdminOfCurrentGroup(userId);
  }
}
