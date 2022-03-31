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
import {GroupMemberLeftEvent} from "../../../backend/notificatinos/notifications/group-member-left-event";
import {GroupMemberJoined} from "../../../backend/notificatinos/notifications/group-member-joined";
import {GroupEdited} from "../../../backend/notificatinos/notifications/group-edited";
import {ServerNotificationSubscriberService} from "../../../backend/notificatinos/server-notification-subscriber.service";
import {PaymentInitialized} from "../../../backend/notificatinos/notifications/payment-initialized";
import {GroupState} from "../../../model/group/group-state";
import {ProgressBarService} from "../../progress-bar.service";
import {PaymentsService} from "../../../backend/payments/payments.service";
import {ShareGroupComponent} from "./share-group/share-group.component";
import {EditGroupComponent} from "./edit-group/edit-group.component";
import {GroupPaymentComponent} from "./group-payment/group-payment.component";
import {ConfirmPaymentComponent} from "./confirm-payment/confirm-payment.component";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {

  constructor(
    public groupState: GroupRepositoryService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private applicationRef: ApplicationRef,
    private frontendHost: FrontendUsingRoutesService,
    private eventSubscriber: ServerNotificationSubscriberService,
    private progressBar: ProgressBarService,
    private paymentService: PaymentsService,
    public auth: Authentication,
  ){}

  ngOnInit(): void {
    this.onMemberLeft();
    this.onMemberJoined();
    this.onGroupDeleted();
    this.onGroupEdited();
    this.onPaymentInitialized();
  }

  public leaveGroup() {
    this.groupsApi.leaveGroup({groupId: this.currentGroup().groupId, ignoreThis: ""}).subscribe(res => {
      this.groupState.clear();
    });
  }

  public currentGroup(): Group {
    return this.groupState.getCurrentGroup();
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
      err => window.alert("Error try later. cause of error: " + err)
    );
  }

  private onMemberLeft(): void {
    this.eventSubscriber.subscribe<GroupMemberLeftEvent>('group-member-left', (groupMemberLeft) => {
      const userId: string = groupMemberLeft.userId;

      console.log("Hola");
      console.log(userId)
      console.log(this.auth.getUserId());

      if(userId == this.auth.getUserId()){
        this.onKicked();
      }else{
        this.groupState.deleteGroupMemberById(userId);
      }

      this.refreshChangesInUI();
    });
  }

  private onKicked(): void {
    window.alert("You have been kicked from the group!");

    this.groupState.clear();
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

  private onPaymentInitialized(): void{
    this.eventSubscriber.subscribe<PaymentInitialized>('group-payment-initialized', (event) => {
      this.groupState.setGroupState(GroupState.PAYING);
      this.progressBar.isLoading.next(true);

      this.refreshChangesInUI();

      this.openGroupPaymentInitializedModal();
    });
  }

  private onGroupEdited() {
    this.eventSubscriber.subscribe<GroupEdited>('group-edited', event => {
      const group: Group = event.group;

      this.groupState.setCurrentGroup(group);
      this.refreshChangesInUI();
    });
  }

  public openEditGroupModal(): void {
    this.modalService.open(EditGroupComponent);
  }

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
  }

  public openShareGroupModal(): void {
    const shareGroupModal = this.modalService.open(ShareGroupComponent);
    shareGroupModal.componentInstance.linkGroup = this.getURLForJoiningGroup();
  }

  public openGroupPaymentInitializedModal(): void {
    this.modalService.open(GroupPaymentComponent);
  }

  public openModalConfirmPayment(): void {
    const modal = this.modalService.open(ConfirmPaymentComponent);
    modal.componentInstance.paymentConfirmed.subscribe(() => {
      this.makePayment();
    });
  }
}
