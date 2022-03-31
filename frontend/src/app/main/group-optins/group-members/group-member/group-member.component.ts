import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from "../../../../../model/user/user";
import {GroupRepositoryService} from "../../../group-repository.service";
import {Authentication} from "../../../../../backend/users/authentication/authentication";

@Component({
  selector: 'app-group-member',
  templateUrl: './group-member.component.html',
  styleUrls: ['./group-member.component.css']
})
export class GroupMemberComponent implements OnInit {
  @Input() groupMember: User;
  @Output() onKick: EventEmitter<string> = new EventEmitter<string>();

  constructor(
    public groupState: GroupRepositoryService,
    public auth: Authentication,
  ) { }

  ngOnInit(): void {
  }

  public isAdmin(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.groupMember.userId);
  }

  isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }

  kickGroupMember() {
    this.onKick.emit(this.groupMember.userId);
  }
}
