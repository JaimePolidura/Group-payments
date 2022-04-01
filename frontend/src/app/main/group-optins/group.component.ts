import {ApplicationRef, Component, OnInit, ViewChild} from '@angular/core';
import {GroupRepositoryService} from "../group-repository.service";
import {Group} from "../../../model/group/group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../backend/groups/groups-api.service";
import {Authentication} from "../../../backend/users/authentication/authentication";
import {GroupPaymentRequest} from "../../../backend/groups/request/group-payment-request";
import {GetGroupMemberByUserIdRequest} from "../../../backend/groups/request/get-group-member-by-user-id-request";
import {GroupMemberLeftEvent} from "../../../backend/notificatinos/notifications/groups/group-member-left-event";
import {GroupMemberJoined} from "../../../backend/notificatinos/notifications/groups/group-member-joined";
import {GroupEdited} from "../../../backend/notificatinos/notifications/groups/group-edited";
import {ServerNotificationSubscriberService} from "../../../backend/notificatinos/server-notification-subscriber.service";
import {GroupPaymentInitialized} from "../../../backend/notificatinos/notifications/groups/payment/group-payment-initialized";
import {GroupState} from "../../../model/group/group-state";
import {ProgressBarService} from "../../loading-progress-bar/progress-bar.service";
import {GroupPaymentComponent} from "./group-payment/group-payment.component";
import {ConfirmPaymentComponent} from "./confirm-payment/confirm-payment.component";
import {GroupMemberKicked} from "../../../backend/notificatinos/notifications/groups/group-member-kicked";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  constructor(
    public groupState: GroupRepositoryService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private applicationRef: ApplicationRef,
    private eventSubscriber: ServerNotificationSubscriberService,
    private progressBar: ProgressBarService,
    public auth: Authentication,
  ){}

  ngOnInit(): void {
    this.onMemberLeft();
    this.onMemberJoined();
    this.onGroupDeleted();
    this.onGroupEdited();
    this.onPaymentInitialized();
    this.onGroupMemberKicked();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
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
      this.groupState.deleteGroupMemberById(groupMemberLeft.userId);
      this.refreshChangesInUI();
    });
  }

  private onGroupMemberKicked(): void {
    this.eventSubscriber.subscribe<GroupMemberKicked>('group-member-kicked', res => {
      if(res.userId != this.auth.getUserId()){ //Other member has been kicked
        this.groupState.deleteGroupMemberById(res.userId);
        this.refreshChangesInUI();
      }else{ //User authenticated has been kicked
        window.alert("You have been kicked!");
        this.groupState.clear();
      }
    });
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

  private onPaymentInitialized(): void{
    this.eventSubscriber.subscribe<GroupPaymentInitialized>('group-payment-initialized', (event) => {
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

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
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
