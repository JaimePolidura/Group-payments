import { Component, OnInit } from '@angular/core';
import {GroupRepositoryService} from "../../group-repository.service";
import {KickGroupMemberRequest} from "../../../../backend/groups/request/kick-group-member-request";
import {User} from "../../../../model/user/user";
import {GroupsApiService} from "../../../../backend/groups/groups-api.service";

@Component({
  selector: 'app-group-members',
  templateUrl: './group-members.component.html',
  styleUrls: ['./group-members.component.css']
})
export class GroupMembersComponent implements OnInit {

  constructor(
    public groupState: GroupRepositoryService,
    private groupApi: GroupsApiService,
  ) { }

  ngOnInit(): void {
  }

  public kickGroupMember(userId: string): void {
    const request: KickGroupMemberRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      userIdToKick: userId
    };

    // @ts-ignore
    const userDeleted: User = this.groupState.deleteGroupMemberById(userId);

    this.groupApi.kickGroupMember(request).subscribe(res => {}, err => {
      this.groupState.addMember(userDeleted);
    });
  }
}
