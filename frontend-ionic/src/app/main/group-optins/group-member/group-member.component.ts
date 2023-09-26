import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from "../../../../shared/users/model/user";
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {SlideLeftToRight} from "../../../_shared/animatinos/slide-left-to-right";

@Component({
  selector: 'app-group-member',
  templateUrl: './group-member.component.html',
  styleUrls: ['./group-member.component.css'],
  animations: [SlideLeftToRight()]
})
export class GroupMemberComponent implements OnInit {
  @Input() groupMember: User;
  @Output() onKick: EventEmitter<string> = new EventEmitter<string>();

  constructor(
    public groupState: GroupCacheService,
    public auth: AuthenticationCacheService,
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
