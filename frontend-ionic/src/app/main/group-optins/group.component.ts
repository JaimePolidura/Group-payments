import {ApplicationRef, Component, OnInit, ViewChild} from '@angular/core';
import {GroupCacheService} from "../../../shared/groups/group-cache.service";
import {Group} from "../../../shared/groups/model/group";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {GroupsApiService} from "../../../shared/groups/groups-api.service";
import {AuthenticationApiService} from "../../../shared/auth/authentication-api.service";
import {GroupPaymentRequest} from "../../../shared/groups/api/request/group-payment-request";
import {GetGroupMemberByUserIdRequest} from "../../../shared/groups/api/request/get-group-member-by-user-id-request";
import {GroupMemberLeftEvent} from "../../../shared/groups/notifications/group-member-left-event";
import {GroupMemberJoined} from "../../../shared/groups/notifications/group-member-joined";
import {GroupEdited} from "../../../shared/groups/notifications/group-edited";
import {ServerNotificationSubscriberService} from "../../../shared/notificatinos/online/server-notification-subscriber.service";
import {GroupPaymentInitialized} from "../../../shared/groups/notifications/group-payment-initialized";
import {GroupState} from "../../../shared/groups/model/group-state";
import {ProgressBarService} from "../../_shared/loading-progress-bar/progress-bar.service";
import {GroupPaymentComponent} from "./group-payment/group-payment.component";
import {ConfirmPaymentComponent} from "./confirm-payment/confirm-payment.component";
import {GroupMemberKicked} from "../../../shared/groups/notifications/group-member-kicked";
import {AuthenticationCacheService} from "../../../shared/auth/authentication-cache.service";
import {KickGroupMemberRequest} from "../../../shared/groups/api/request/kick-group-member-request";
import {User} from "../../../shared/users/model/user";
import {RecentUsersService} from "../../../shared/recentusers/recent-users.service";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  constructor(
    public groupState: GroupCacheService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private applicationRef: ApplicationRef,
    private eventSubscriber: ServerNotificationSubscriberService,
    private progressBar: ProgressBarService,
    public auth: AuthenticationCacheService,
    private recentUserCache: RecentUsersService,
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
      groupId: this.groupState.getGroup().groupId,
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
    });
  }

  private onGroupMemberKicked(): void {
    this.eventSubscriber.subscribe<GroupMemberKicked>('group-member-kicked', res => {
      if(res.userId != this.auth.getUserId() && !this.groupState.isAdminOfCurrentGroup(this.auth.getUserId())){ //Other member has been kicked
        this.groupState.deleteGroupMemberById(res.userId);
        this.refreshChangesInUI();
      }else if(res.userId == this.auth.getUserId()){ //User authenticated has been kicked
        window.alert("You have been kicked!");
        this.groupState.clear();
      }
    });
  }

  private onMemberJoined(): void {
    this.eventSubscriber.subscribe<GroupMemberJoined>('group-member-joined', (groupMemberJoined) => {
      const userId = groupMemberJoined.userId;
      const request: GetGroupMemberByUserIdRequest = {userId: userId, groupId: this.groupState.getGroup().groupId};

      this.groupsApi.getGroupMemberByUserId(request).subscribe(res => {
        this.recentUserCache.save(res.member);
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
      this.progressBar.start();

      this.refreshChangesInUI();

      this.openGroupPaymentInitializedModal();
    });
  }

  private onGroupEdited() {
    this.eventSubscriber.subscribe<GroupEdited>('group-edited', event => {
      const group: Group = event.group;

      this.groupState.setGroup(group);
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

  public kickGroupMember(userId: string): void {
    const request: KickGroupMemberRequest = {
      groupId: this.groupState.getGroup().groupId,
      userIdToKick: userId
    };

    // @ts-ignore
    const userDeleted: User = this.groupState.deleteGroupMemberById(userId);

    this.groupsApi.kickGroupMember(request).subscribe(res => {}, err => {
      this.groupState.addMember(userDeleted);
    });
  }

  public openModalConfirmPayment(): void {
    const modal = this.modalService.open(ConfirmPaymentComponent);
    modal.componentInstance.paymentConfirmed.subscribe(() => {
      this.makePayment();
    });
  }
}
