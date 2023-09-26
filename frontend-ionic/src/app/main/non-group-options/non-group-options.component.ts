import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthenticationApiService} from "../../../shared/auth/authentication-api.service";
import {Router} from "@angular/router";
import {MakeTransferModalComponent} from "./make-transfer-modal/make-transfer-modal.component";
import {JoinGroupModalComponent} from "./join-group-modal/join-group-modal.component";
import {CreateGroupModalComponent} from "./create-group-modal/create-group-modal.component";
import {InvitationsApiService} from "../../../shared/invitations/invitations-api.service";
import {InvitationsCacheService} from "../../../shared/invitations/invitations-cache.service";
import {AuthenticationCacheService} from "../../../shared/auth/authentication-cache.service";

@Component({
  selector: 'app-non-group-options',
  templateUrl: './non-group-options.component.html',
  styleUrls: ['./non-group-options.component.css']
})
export class NonGroupOptionsComponent implements OnInit {
  constructor(
    public modalService: NgbModal,
    public authApi: AuthenticationApiService,
    private router: Router,
    private invitationService: InvitationsApiService,
    public invitationState: InvitationsCacheService,
    public authCache: AuthenticationCacheService,
  ){}

  ngOnInit(): void {
    this.setupPendingInvitations();
  }

  public logout(): void {
    this.authApi.logout(() => {
      this.router.navigateByUrl("/");
    });
  }

  private setupPendingInvitations(): void {
    this.invitationService.getInvitationsByUserId().subscribe(res => {});
  }

  private closeModal(): void {
    this.modalService.dismissAll();
  }

  public openMakeTransferModal(): void {
    this.modalService.open(MakeTransferModalComponent);
  }

  public openCreateGroupModal(): void {
    this.modalService.open(CreateGroupModalComponent);
  }

  public goToPaymentHistory() {
    this.router.navigateByUrl("paymenthistory");
  }

  public goToAccountSettings(): void {
    this.router.navigateByUrl("accountsettings");
  }

  public openJoinGroupModal(): void {
    this.modalService.open(JoinGroupModalComponent);
  }
}
