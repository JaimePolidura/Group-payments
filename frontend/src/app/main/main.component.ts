import { Component, OnInit } from '@angular/core';
import {Authentication} from "../../backend/authentication/authentication";
import {GroupsApiService} from "../../backend/groups/groups-api.service";
import {GroupStateService} from "./group-state.service";
import {ActivatedRoute, ActivatedRouteSnapshot, Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  constructor(
    public auth: Authentication,
    private groupService: GroupsApiService,
    public groupState: GroupStateService,
    private actualRoute: ActivatedRoute,
    private router: Router,
  ) { }

  ngOnInit(): void {
    const userId: string = this.auth.getUserId();

    this.groupService.getCurrentGroup(userId).subscribe(res => {
      if(res.group != undefined) {
        this.groupState.setCurrentGroup(res.group);

        this.groupService.getGroupMembersByGroupId(res.group.groupId).subscribe(res =>  {
          this.groupState.setCurrentGroupMembers(res.members);
        });
      }
    });
  }
}
