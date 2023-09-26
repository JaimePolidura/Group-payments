import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {DeleteAccountStatus} from "./delete-account-status";
import {UsersApiService} from "../../../shared/users/users-api.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationApiService} from "../../../shared/auth/authentication-api.service";
import {InvitationsCacheService} from "../../../shared/invitations/invitations-cache.service";

@Component({
  selector: 'app-delete-account-confirm-modal',
  templateUrl: './delete-account-confirm-modal.component.html',
  styleUrls: ['./delete-account-confirm-modal.component.css']
})
export class DeleteAccountConfirmModalComponent implements OnInit {
  public deleteAccountStatus: DeleteAccountStatus;

  constructor(private modalService: NgbModal,
              private usersService: UsersApiService,
              private acutalRoute: ActivatedRoute,
              private authneticationService: AuthenticationApiService,
              private invitationService: InvitationsCacheService,
              private router: Router,
  ) { }

  ngOnInit(): void {
    this.deleteAccountStatus = DeleteAccountStatus.PENDING;
  }

  public deleteAccount(): void {
    this.acutalRoute.queryParams.subscribe(params => {
      const token: string = params['token'];

      //For some reason this is called twice
      if(this.deleteAccountStatus == DeleteAccountStatus.DONE) return;

      this.usersService.confirmDeleteAccont({token: token}).subscribe(res => {
        this.deleteAccountStatus = DeleteAccountStatus.DONE;
        this.authneticationService.logout(() => {});
        this.invitationService.clear();
        localStorage.clear();

        this.router.navigateByUrl("login");
      },err => {
        console.log(err);
        this.deleteAccountStatus = DeleteAccountStatus.ERROR;
      });
    });
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }
}
