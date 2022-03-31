import { Component, OnInit } from '@angular/core';
import {ShareGroupComponent} from "./share-group/share-group.component";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {FrontendUsingRoutesService} from "../../../../frontend-using-routes.service";
import {GroupRepositoryService} from "../../group-repository.service";
import {EditGroupComponent} from "./edit-group/edit-group.component";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {GroupsApiService} from "../../../../backend/groups/groups-api.service";
import {LeaveGroupRequest} from "../../../../backend/groups/request/leave-group-request";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {

  constructor(
    private modalService: NgbModal,
    private frontendUsingRoutes: FrontendUsingRoutesService,
    public groupState: GroupRepositoryService,
    public auth: Authentication,
    private groupApi: GroupsApiService,
  ) { }

  ngOnInit(): void {
  }

  public openShareGroupModal(): void {
    const shareGroupModal = this.modalService.open(ShareGroupComponent);
    shareGroupModal.componentInstance.linkGroup = this.getURLForJoiningGroup();
  }

  public getURLForJoiningGroup(): string{
    return `${this.frontendUsingRoutes.USING}/join/${this.groupState.getCurrentGroup().groupId}`;
  }

  public leaveGroup(): void {
    const request: LeaveGroupRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      ignoreThis: ""
    }

    this.groupApi.leaveGroup(request).subscribe(res => {
      this.groupState.clear();
    });
  }

  public openEditGroupModal(): void {
    this.modalService.open(EditGroupComponent);
  }
}
