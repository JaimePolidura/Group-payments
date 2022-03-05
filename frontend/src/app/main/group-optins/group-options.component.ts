import { Component, OnInit } from '@angular/core';
import {GroupStateService} from "../group-state.service";
import {Group} from "../../../model/group";
import {User} from "../../../model/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {

  constructor(
    private groupState: GroupStateService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
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
}
