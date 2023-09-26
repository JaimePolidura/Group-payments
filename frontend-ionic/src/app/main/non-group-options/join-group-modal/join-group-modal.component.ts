import {Component, OnInit} from '@angular/core';
import {GroupsApiService} from "../../../../shared/groups/groups-api.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {InvitationsApiService} from "../../../../shared/invitations/invitations-api.service";
import {UsersApiService} from "../../../../shared/users/users-api.service";
import {InvitationsCacheService} from "../../../../shared/invitations/invitations-cache.service";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";

@Component({
  selector: 'app-join-group-modal',
  templateUrl: './join-group-modal.component.html',
  styleUrls: ['./join-group-modal.component.css']
})
export class JoinGroupModalComponent implements OnInit {
  constructor(
    private groupService: GroupsApiService,
    private auth: AuthenticationCacheService,
    private groupState: GroupCacheService,
    private modalService: NgbModal,
    private invitationsService: InvitationsApiService,
    private userService: UsersApiService,
    public invitationState: InvitationsCacheService,
  ) { }

  ngOnInit(): void {
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }
}
