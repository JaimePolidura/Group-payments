import {ApplicationRef, Component, OnInit, ViewChild} from '@angular/core';
import {GroupRepositoryService} from "../group-repository.service";
import {Group} from "../../../model/group/group";
import {User} from "../../../model/user/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {KickGroupMemberRequest} from "../../../backend/groups/request/kick-group-member-request";
import {Authentication} from "../../../backend/users/authentication/authentication";
import {GroupPaymentRequest} from "../../../backend/groups/request/group-payment-request";
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
import {GroupState} from "../../../model/group/group-state";
import {ProgressBarService} from "../../progress-bar.service";
import {PaymentsService} from "../../../backend/payments/payments.service";
import {PaymentDone} from "../../../backend/eventlistener/events/payment-done";
import {ErrorWhileMemberPaying} from "../../../backend/eventlistener/events/error-while-member-paying";
import {AppPayingAdminDone} from "../../../backend/eventlistener/events/app-paying-admin-done";
import {ErrorWhilePayingToAdmin} from "../../../backend/eventlistener/events/error-while-paying-to-admin";
import {MemberPayingAppDone} from "../../../backend/eventlistener/events/member-paying-app-done";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {
  @ViewChild('errorPaymentModal') private errorPaymentModal: any;
  @ViewChild('paymentInitializedModal') private paymentInitializedModal: any;

  public editGroupForm: FormGroup;
  public logEventsGroupPayments: {body: string, error: boolean}[];
  public donePaying: boolean;

  constructor(
    public groupState: GroupRepositoryService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private serverEventListener: ServerEventListener,
    private applicationRef: ApplicationRef,
    private frontendHost: FrontendUsingRoutesService,
    private eventSubscriber: ServerEventsSubscriberService,
    private progressBar: ProgressBarService,
    private paymentService: PaymentsService,
    public auth: Authentication,
  ){}

  ngOnInit(): void {
    this.logEventsGroupPayments = [];
    this.donePaying = false;

    this.onMemberLeft();
    this.onMemberJoined();
    this.onGroupDeleted();
    this.onGroupEdited();
    this.onPaymentInitialized();
    this.onAllGroupPaymentDone();
    this.onErrorWhileMemberPaying();
    this.onAppPayingAdmin();
    this.onErrorWhilePayingToAdmin();
    this.onMemberPaidToApp();

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
    const request: GroupPaymentRequest = {
      groupId: this.groupState.getCurrentGroup().groupId,
      userId: this.auth.getUserId(),
    };

    this.closeModal();

    this.groupsApi.makeGroupPayment(request).subscribe(
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
      this.progressBar.isLoading.next(true);

      this.refreshChangesInUI();
    });
  }

  private onMemberPaidToApp(): void {
    this.eventSubscriber.subscribe<MemberPayingAppDone>('group-payment-member-app-done', res => {

      if(this.auth.getUserId() == res.groupMemberUserId)
        this.logEventsGroupPayments.push({error: false, body: `You have been charged ${res.money}`});
    })
  }

  private onErrorWhileMemberPaying(): void {
    this.eventSubscriber.subscribe<ErrorWhileMemberPaying>('group-payment-error-member-paying', res => {
      const username = this.groupState.getGroupMemberUserByUserId(res.groupId).username;

      if(this.isLoggedUserAdminOfCurrentGroup())
        this.logEventsGroupPayments.push({error: true, body: `${username} couldnt be charged. Reason: ${res.errorMessage}`});

      if(res.groupMemberUserId == this.auth.getUserId())
        this.logEventsGroupPayments.push({error: true, body: `You couldnt be charged. Reason: ${res.errorMessage}`});
    });
  }

  private onAppPayingAdmin(): void {
    this.eventSubscriber.subscribe<AppPayingAdminDone>('group-payment-app-admin-done', res => {
      if(this.isLoggedUserAdminOfCurrentGroup())
        this.logEventsGroupPayments.push({error: false, body: `You recieved the payment ${res.money}`});
    });
  }

  private onErrorWhilePayingToAdmin(): void {
    this.eventSubscriber.subscribe<ErrorWhilePayingToAdmin>('group-payment-error-paying-admin', res => {
      if(this.isLoggedUserAdminOfCurrentGroup()){
        this.progressBar.isLoading.next(false);
        this.logEventsGroupPayments.push({
          error: true,
          body: `You couldnt recieve the payment all members have been charged the funds havent get lost,
            they have been retained. Contact support to recieve it. error message: ${res.errorMessage}`
        });
      }
    });
  }

  private onAllGroupPaymentDone(): void {
    this.eventSubscriber.subscribe<PaymentDone>("group-payment-all-done", res => {
      this.donePaying = true;
      this.progressBar.isLoading.next(false);
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
