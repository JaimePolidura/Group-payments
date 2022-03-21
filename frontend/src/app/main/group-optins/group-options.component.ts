import {ApplicationRef, Component, OnInit, ViewChild} from '@angular/core';
import {GroupRepositoryService} from "../group-repository.service";
import {Group} from "../../../model/group";
import {User} from "../../../model/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {KickGroupMemberRequest} from "../../../backend/groups/request/kick-group-member-request";
import {Authentication} from "../../../backend/authentication/authentication";
import {MakePaymentRequest} from "../../../backend/groups/request/make-payment-request";
import {GetGroupMemberByUserIdRequest} from "../../../backend/groups/request/get-group-member-by-user-id-request";
import {FrontendUsingRoutesService} from "../../../frontend-using-routes.service";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {EditGroupRequest} from "../../../backend/groups/request/edit-group-request";
import {GroupMemberLeftEvent} from "../../../backend/eventlistener/events/group-member-left-event";
import {GroupMemberJoined} from "../../../backend/eventlistener/events/group-member-joined";
import {GroupEdited} from "../../../backend/eventlistener/events/group-edited";
import {ServerEventsSubscriberService} from "../../../backend/eventlistener/server-events-subscriber.service";
import {ServerEventListener} from "../../../backend/eventlistener/server-event-listener";
import {PaymentInitialized} from "../../../backend/eventlistener/events/payment-initialized";
import {GroupState} from "../../../model/group-state";
import {HttpLoadingService} from "../../../backend/http-loading.service";
import {PaymentsService} from "../../../backend/payments/payments.service";
import {PaymentDone} from "../../../backend/eventlistener/events/payment-done";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {
  @ViewChild('errorPaymentModal') private errorPaymentModal: any;
  @ViewChild('paymentInitializedModal') private paymentInitializedModal: any;

  editGroupForm: FormGroup;

  constructor(
    public groupState: GroupRepositoryService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private auth: Authentication,
    private serverEventListener: ServerEventListener,
    private applicationRef: ApplicationRef,
    private frontendHost: FrontendUsingRoutesService,
    private eventSubscriber: ServerEventsSubscriberService,
    private httpLoader: HttpLoadingService,
    private paymentService: PaymentsService,
  ){}

  ngOnInit(): void {
    this.onMemberLeft();
    this.onMemberJoined();
    this.onGroupDeleted();
    this.onGroupEdited();
    this.onPaymentInitialized();
    this.onPaymentDone();

    this.serverEventListener.connect();

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
    this.serverEventListener.disconnect();

    this.groupsApi.leaveGroup({groupId: this.currentGroup().groupId, ignoreThis: ""}).subscribe(res => {
      this.groupState.clear();
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

    this.paymentService.makePayment(request).subscribe(
      () => {},
      err => this.onPaymentFailure(err)
    );
  }

  private onPaymentFailure(err: any): void {
    this.modalService.open(this.errorPaymentModal);
  }

  private onMemberLeft(): void {
    this.eventSubscriber.subscribe<GroupMemberLeftEvent>('group-member-left', (groupMemberLeft) => {
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

    this.groupState.clear();
    this.serverEventListener.disconnect()
    this.refreshChangesInUI()
  }

  private onMemberJoined(): void {
    this.eventSubscriber.subscribe<GroupMemberJoined>('group-member-joined', (groupMemberJoined) => {
      const userId = groupMemberJoined.userId;
      const request: GetGroupMemberByUserIdRequest = {userId: userId, groupId: this.groupState.getCurrentGroup().groupId};

      this.groupsApi.getGroupMemberByUserId(request).subscribe(res => {
        this.groupState.addMember(res.member);
        this.refreshChangesInUI();
      });
    });
  }

  private onGroupDeleted(): void {
    this.eventSubscriber.subscribe('group-deleted', (event) => {
      this.groupState.clear();
      this.serverEventListener.disconnect();

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

  private onPaymentInitialized(): void{
    this.eventSubscriber.subscribe<PaymentInitialized>('group-payment-initialized', (event) => {
      this.modalService.open(this.paymentInitializedModal);
      this.groupState.setGroupState(GroupState.PAYING);
      this.httpLoader.isLoading.next(true);

      this.refreshChangesInUI();
    });
  }

  private onPaymentDone(): void {
    this.eventSubscriber.subscribe<PaymentDone>("group-payment-all-done", res => {
      this.modalService.dismissAll();
      this.httpLoader.isLoading.next(false);
    });
  }

  private onGroupEdited() {
    this.eventSubscriber.subscribe<GroupEdited>('group-edited', event => {
      const group: Group = event.group;

      this.groupState.setCurrentGroup(group);
      this.refreshChangesInUI();
    });
  }

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
  }
}
