import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {CreateGroupRequest} from "../../../../shared/groups/api/request/create-group-request";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../../shared/groups/groups-api.service";
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {Router} from "@angular/router";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {ReplaySubject} from "rxjs";

@Component({
  selector: 'app-create-group-modal',
  templateUrl: './create-group-modal.component.html',
  styleUrls: ['./create-group-modal.component.css']
})
export class CreateGroupModalComponent implements OnInit, AfterViewInit {
  public usersEmailsToInvite: string[];
  public createGroupForm: FormGroup;

  public $clearSearchResults: ReplaySubject<void>;

  @ViewChild('createGroupMoneyInput') public createGroupMoneyInput: ElementRef<HTMLInputElement>;

  constructor(
    public modalService: NgbModal,
    public auth: AuthenticationCacheService,
    private groupService: GroupsApiService,
    private groupState: GroupCacheService,
    private router: Router,
  ){}

  ngOnInit(): void {
    this.setUpCreateGroupForm();
    this.usersEmailsToInvite = [];
    this.$clearSearchResults = new ReplaySubject<void>();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.createGroupMoneyInput.nativeElement.focus();
    }, 0);
  }

  private setUpCreateGroupForm(): void {
    this.createGroupForm = new FormGroup({
      money: new FormControl('', [Validators.required, Validators.min(1)]),
      title: new FormControl('GroupPayment', [Validators.required, Validators.maxLength(15)]),
      userEmailToInvite: new FormControl(''),
    });
  }
  get money(): AbstractControl { return <AbstractControl>this.createGroupForm.get('money'); }
  get title(): AbstractControl { return <AbstractControl>this.createGroupForm.get('title'); }
  get userEmailToInvite(): AbstractControl { return <AbstractControl>this.createGroupForm.get('userEmailToInvite'); }

  public createGroup(): void {
    this.closeModal();

    const createGroupRequest: CreateGroupRequest = {
      money: this.money?.value,
      title: this.title?.value,
      usersEmailToInvite: this.usersEmailsToInvite
    }

    this.groupService.createGroup(createGroupRequest).subscribe(res => {});
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  public userToInviteTyped(email: string): void {
    this.userEmailToInvite.setValue('');
    this.$clearSearchResults.next();

    if(!email || email === '' || this.notAdded(email)){
      this.usersEmailsToInvite.push(email);
    }
  }

  private notAdded(userEmail: string): boolean {
    return this.usersEmailsToInvite.filter(it => it === userEmail).length == 0;
  }
}
