import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {GroupCacheService} from "../../../../../shared/groups/group-cache.service";
import {UsersApiService} from "../../../../../shared/users/users-api.service";
import {InvitationsApiService} from "../../../../../shared/invitations/invitations-api.service";
import {AuthenticationCacheService} from "../../../../../shared/auth/authentication-cache.service";
import {RecentUser} from "../../../../../shared/recentusers/model/recent-user";
import {ToastService} from "../../../../_shared/toast/toast-service";
import {ReplaySubject} from "rxjs";
import {RecentUsersService} from "../../../../../shared/recentusers/recent-users.service";

@Component({
  selector: 'app-invite-group',
  templateUrl: './invite-group.component.html',
  styleUrls: ['./invite-group.component.css']
})
export class InviteGroupComponent implements OnInit, AfterViewInit {
  @ViewChild('inviteToGroupEmailInput') public inviteToGroupEmailInput: ElementRef<HTMLInputElement>;
  public inviteForm: FormGroup;
  public userIdToInvite: string;
  public $clearSearchResults: ReplaySubject<void>;

  constructor(
    public modalService: NgbModal,
    private auth: AuthenticationCacheService,
    private groupState: GroupCacheService,
    private userService: UsersApiService,
    private invitationsService: InvitationsApiService,
    private recentUsers: RecentUsersService,
    private toastService: ToastService,
  ) { }

  ngOnInit(): void {
    this.$clearSearchResults = new ReplaySubject<void>();

    this.setupInviteForm();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.inviteToGroupEmailInput.nativeElement.focus();
    }, 0);
  }

  private setupInviteForm(): void {
    this.inviteForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email])
    });
  }
  get email(): AbstractControl {return <AbstractControl>this.inviteForm.get('email');}

  public checkIfEmailsDestiantionValid() {
    if(!this.email.valid || !this.email.value) return;

    const userToInviteEmail: string = this.email.value;

    if(userToInviteEmail == this.auth.getEmail()){
      this.email.setErrors({cannotBeTheSame: true});
      return;
    }

    if(this.groupState.existsMemberByEmail(userToInviteEmail)){
      this.email.setErrors({userInGroup: true});
      return;
    }

    this.userService.getUserIdByEmail(userToInviteEmail).subscribe(res => {
      this.email.setErrors(null);
      this.userIdToInvite = res.userId;
      this.$clearSearchResults.next();

    }, err => this.email.setErrors({someError: true}));
  }

  public inviteUser() {
    let groupId: string = this.groupState.getGroup().groupId;
    this.closeModal();
    this.toastService.info('User invited', '');

    this.invitationsService.createInvitation({groupId: groupId, toUserId: this.userIdToInvite}).subscribe(res => {}, err => {
       this.recentUsers.deleteById(this.userIdToInvite);
    });
  }

  public copyToClipboard(toCopy: any) {
    navigator.clipboard.writeText(toCopy);
    this.closeModal();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  public selectedUserToInvite(user: RecentUser) {
    this.userIdToInvite = user.userId;
    this.inviteUser();
  }
}
