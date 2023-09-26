import {Component, Input, OnInit} from '@angular/core';
import {Invitation} from "../../../../../shared/invitations/model/invitation";
import {InvitationsApiService} from "../../../../../shared/invitations/invitations-api.service";
import {InvitationsCacheService} from "../../../../../shared/invitations/invitations-cache.service";
import {GroupsApiService} from "../../../../../shared/groups/groups-api.service";
import {GroupCacheService} from "../../../../../shared/groups/group-cache.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-pending-invitation',
  templateUrl: './pending-invitation.component.html',
  styleUrls: ['./pending-invitation.component.css']
})
export class PendingInvitationComponent implements OnInit {
  @Input() invitation: Invitation;

  constructor(
    private invitationSerivce: InvitationsApiService,
    private invitationStateRepository: InvitationsCacheService,
    private groupService: GroupsApiService,
    private groupState: GroupCacheService,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {
  }

  public acceptInvitation(): void {
    this.invitationStateRepository.deleteById(this.invitation.invitationId);
    this.closeModal();

    this.invitationSerivce.accpet({invitationId: this.invitation.invitationId}).subscribe(res => {});
  }

  public rejectInvitation(): void {
    this.invitationStateRepository.deleteById(this.invitation.invitationId);
    this.closeModal();

    this.invitationSerivce.reject({invitationId: this.invitation.invitationId}).subscribe(res => {
      //TODO
    });
  }

  private closeModal(): void {
    this.modalService.dismissAll();
  }
}
