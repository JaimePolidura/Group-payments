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
import {GroupMemberJoined} from "../../../backend/events/model/group-member-joined";
import {GetGroupMemberByUserIdRequest} from "../../../backend/groups/request/get-group-member-by-user-id-request";

@Component({
  selector: 'app-group-options',
  templateUrl: './group-options.component.html',
  styleUrls: ['./group-options.component.css']
})
export class GroupOptionsComponent implements OnInit {
  @ViewChild('errorPaymentModal') private errorPaymentModal: any;

  constructor(
    public groupState: GroupStateService,
    public modalService: NgbModal,
    private groupsApi: GroupsApiService,
    private auth: Authentication,
    private serverSentEvents: ServerSentEventsService,
    private applicationRef: ApplicationRef,
  ){}

  ngOnInit(): void {
    this.onMemberLeft();
    this.onMemberJoined();

    this.serverSentEvents.connect();
  }

  public leaveGroup() {
    this.groupsApi.leaveGroup({groupId: this.currentGroup().groupId, ignoreThis: ""}).subscribe(res => {
      this.serverSentEvents.disconnect();
      this.groupState.clearState();
    });
  }

  public currentGroup(): Group {
    return this.groupState.getCurrentGroup();
  }

  public currentGroupMembers(): User[] {
    return this.groupState.getCurrentGroupMembers();
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

  public onMemberLeft(): void {
    this.serverSentEvents.subscribe('group-member-left', (groupMemberLeft) => {
      // @ts-ignore
      this.groupState.deleteGroupMemberById(groupMemberLeft.userId);
      this.refreshChangesInUI();
    });
  }

  public onMemberJoined(): void {
    this.serverSentEvents.subscribe('group-member-joined', (groupMemberJoined) => {
      // @ts-ignore
      const userId = groupMemberJoined.userId;
      const request: GetGroupMemberByUserIdRequest = {userId: userId, groupId: this.groupState.getCurrentGroup().groupId};

      this.groupsApi.getGroupMemberByUserId(request).subscribe(res => {
        this.groupState.addMember(res.member);
        this.refreshChangesInUI();
      });
    });
  }

  //We force angular to update
  //TODO fix
  private refreshChangesInUI(): void {
    this.applicationRef.tick();
  }
}
