import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Authentication} from "../../backend/users/authentication/authentication";
import {GroupsApiService} from "../../backend/groups/groups-api.service";
import {JoinGroupRequest} from "../../backend/groups/request/join-group-request";
import {GroupRepositoryService} from "../main/group-repository.service";

@Component({
  selector: 'app-join-group',
  templateUrl: './join-group.component.html',
  styleUrls: ['./join-group.component.css']
})
export class JoinGroupComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private auth: Authentication,
    private groupsApi: GroupsApiService,
    private router: Router,
  ){ }

  ngOnInit(): void {
    const groupId: string = this.route.snapshot.params["groupId"];

    const request: JoinGroupRequest = {
      groupId: groupId,
      ignoreThis:'',
    }

    this.groupsApi.joinGroup(request).subscribe(res => {
      this.router.navigate(["/main"]);
    });
  }

}
