import { Component, OnInit } from '@angular/core';
import {InviteGroupComponent} from "./invite-group/invite-group.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {EditGroupComponent} from "./edit-group/edit-group.component";
import {GroupsApiService} from "../../../../shared/groups/groups-api.service";
import {LeaveGroupRequest} from "../../../../shared/groups/api/request/leave-group-request";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {FrontendUsingRoutesService} from "../../../frontend-using-routes.service";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {

  constructor(
    private modalService: NgbModal,
    private frontendUsingRoutes: FrontendUsingRoutesService,
    public groupState: GroupCacheService,
    public auth: AuthenticationCacheService,
    private groupApi: GroupsApiService,
  ) { }

  ngOnInit(): void {
  }

  public openShareGroupModal(): void {
    const shareGroupModal = this.modalService.open(InviteGroupComponent);
  }

  public getURLForJoiningGroup(): string{
    return `${this.frontendUsingRoutes.USING}/join/${this.groupState.getGroup().groupId}`;
  }

  public leaveGroup(): void {
    const request: LeaveGroupRequest = {
      groupId: this.groupState.getGroup().groupId,
      ignoreThis: ""
    }

    this.groupApi.leaveGroup(request).subscribe(res => {});
  }

  public openEditGroupModal(): void {
    this.modalService.open(EditGroupComponent);
  }
}
