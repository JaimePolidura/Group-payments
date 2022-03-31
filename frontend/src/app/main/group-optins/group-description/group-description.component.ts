import { Component, OnInit } from '@angular/core';
import {GroupRepositoryService} from "../../group-repository.service";
import {Group} from "../../../../model/group/group";
import {Authentication} from "../../../../backend/users/authentication/authentication";

@Component({
  selector: 'app-group-description',
  templateUrl: './group-description.component.html',
  styleUrls: ['./group-description.component.css']
})
export class GroupDescriptionComponent implements OnInit {

  constructor(
    public groupState: GroupRepositoryService,
    public auth: Authentication,
  ) { }

  ngOnInit(): void {
  }

  public currentGroup(): Group {
    return this.groupState.getCurrentGroup();
  }

}
