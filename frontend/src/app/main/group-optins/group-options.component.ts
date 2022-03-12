import {ApplicationRef, Component, OnInit, ViewChild} from '@angular/core';
import {GroupStateService} from "../group-state.service";
import {Group} from "../../../model/group";
import {User} from "../../../model/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {KickGroupMemberRequest} from "../../../backend/groups/request/kick-group-member-request";
import {Authentication} from "../../../backend/authentication/authentication";
import {MakePaymentRequest} from "../../../backend/groups/request/make-payment-request";
import {ServerSentEventsService} from "../../../backend/events/server-sent-events.service";
import {GetGroupMemberByUserIdRequest} from "../../../backend/groups/request/get-group-member-by-user-id-request";
import {FrontendUsingRoutesService} from "../../../frontend-using-routes.service";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {EditGroupRequest} from "../../../backend/groups/request/edit-group-request";
import {GroupMemberLeftEvent} from "../../../backend/events/model/group-member-left-event";
import {GroupMemberJoined} from "../../../backend/events/model/group-member-joined";
import {GroupEdited} from "../../../backend/events/model/group-edited";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {
  @ViewChild('errorPaymentModal') private errorPaymentModal: any;

  editGroupForm: FormGroup;

  constructor(
    public groupState: GroupStateService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private auth: Authentication,
    private serverSentEvents: ServerSentEventsService,
    private applicationRef: ApplicationRef,
    private frontendHost: FrontendUsingRoutesService,
  ){}

  ngOnInit(): void {
    this.onMemberLeft();
    this.onMemberJoined();
    this.onGroupDeleted();
    this.onGroupEdited();

    this.serverSentEvents.connect();

    this.setUpEditGroupForm();
  }

  private setUpEditGroupForm(): void {
    this.editGroupForm = new FormGroup({
      newMoney: new FormControl(this.currentGroup().money, [Validators.required, Validators.min(0.1), Validators.max(10000)]),
      newDescription: new FormControl(this.currentGroup().description, [Validators.required, Validators.minLength(1), Validators.maxLength(16)])
    });
  }
  get newMoney(): AbstractControl {return <AbstractControl>this.editGroupForm.get('newMoney'); }
  get newDescription(): AbstractControl { return <AbstractControl>this.editGroupForm.get('newDescription'); }

  public leaveGroup() {
    this.serverSentEvents.disconnect();

    this.groupsApi.leaveGroup({groupId: this.currentGroup().groupId, ignoreThis: ""}).subscribe(res => {
      this.groupState.clearState();
    });
  }

  public currentGroup(): Group {
    return this.groupState.getCurrentGroup();
  }

  public copyToClipboard(toCopy: any) {
    navigator.clipboard.writeText(toCopy);
    this.closeModal();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  public kickGroupMember(userId: string): void {
    const request: KickGroupMemberRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      userIdToKick: userId
    };

    // @ts-ignore
    const userDeleted: User = this.groupState.deleteGroupMemberById(userId);

    this.groupsApi.kickGroupMember(request).subscribe(res => {}, err => {
      this.groupState.addMember(userDeleted);
    });
  }

  isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }

  isAdminOfCurrentGroup(userId: string): boolean{
    return this.groupState.isAdminOfCurrentGroup(userId);
  }

  public makePayment(): void {
    const request: MakePaymentRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      userId: this.auth.getUserId(),
    };

    this.closeModal();

    this.groupsApi.makePayment(request).subscribe(
      res => this.onPaymentSuccess(),
      err => this.onPaymentFailure(err)
    );
  }

  private onPaymentSuccess(): void {
  }

  private onPaymentFailure(err: any): void {
    this.modalService.open(this.errorPaymentModal);
  }

  private onMemberLeft(): void {
    this.serverSentEvents.subscribe<GroupMemberLeftEvent>('group-member-left', (groupMemberLeft) => {
      const userId = groupMemberLeft.userId;

      if(userId == this.auth.getUserId()){
        this.onKicked();
      }else{
        this.groupState.deleteGroupMemberById(userId);
        this.refreshChangesInUI();
      }
    });
  }

  private onKicked(): void {
    window.alert("You have been kicked from the group!");

    this.groupState.clearState();
    this.serverSentEvents.disconnect()
    this.refreshChangesInUI()
  }

  private onMemberJoined(): void {
    this.serverSentEvents.subscribe<GroupMemberJoined>('group-member-joined', (groupMemberJoined) => {
      const userId = groupMemberJoined.userId;
      const request: GetGroupMemberByUserIdRequest = {userId: userId, groupId: this.groupState.getCurrentGroup().groupId};

      this.groupsApi.getGroupMemberByUserId(request).subscribe(res => {
        this.groupState.addMember(res.member);
        this.refreshChangesInUI();
      });
    });
  }

  private onGroupDeleted(): void {
    this.serverSentEvents.subscribe('group-deleted', (event) => {
      this.groupState.clearState();
      this.serverSentEvents.disconnect();

      this.refreshChangesInUI();
    });
  }

  public getURLForJoiningGroup(): string{
    return `${this.frontendHost.USING}/join/${this.currentGroup().groupId}`;
  }

  public calculateTotalMoneyPerMember(): number {
    const notOnlyAdminInGroup: boolean = this.groupState.getCurrentGroupMembers().length - 1 > 0;

    return notOnlyAdminInGroup ?
      this.groupState.getCurrentGroup().money / (this.groupState.getCurrentGroupMembers().length - 1) :
      0 ;
  }

  public editGroup() {
    if(!this.isEditGroupFormDataChanged()) return;

    const request: EditGroupRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      newDescription: this.newDescription.value,
      newMoney: this.newMoney.value,
    }

    this.groupsApi.editGroup(request).subscribe(res => {
      this.groupState.setCurrentGroup({
        ...this.currentGroup(),
        money: request.newMoney,
        description: request.newDescription,
      });

      this.refreshChangesInUI();
    });
  }

  private isEditGroupFormDataChanged(): boolean {
    return this.newMoney.value != this.currentGroup().money ||
      this.newDescription.value != this.currentGroup().description;
  }

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
  }

  private onGroupEdited() {
    this.serverSentEvents.subscribe<GroupEdited>('group-edited', event => {
      const group: Group = event.group;

      this.groupState.setCurrentGroup(group);
      this.refreshChangesInUI();
    });
  }
}
