import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  ServerNotificationSubscriberService
} from "../../../../backend/notificatinos/server-notification-subscriber.service";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {GroupPaymentDone} from "../../../../backend/notificatinos/notifications/groups/payment/group-payment-done";
import {ProgressBarService} from "../../../loading-progress-bar/progress-bar.service";
import {GroupRepositoryService} from "../../group-repository.service";
import {MemberPaidToAdmin} from 'src/backend/notificatinos/notifications/groups/payment/member-paid-to-admin';
import {
  ErrorWhilePayingToGroupAdmin
} from 'src/backend/notificatinos/notifications/groups/payment/error-while-paying-to-group-admin';
import {GroupPaymentStatus} from "./group-payment-status";

@Component({
  selector: 'app-group-payment',
  templateUrl: './group-payment.component.html',
  styleUrls: ['./group-payment.component.css']
})
export class GroupPaymentComponent implements OnInit {
  public logEventsGroupPayments: {body: string, error: boolean}[];
  public paymentStatus: GroupPaymentStatus;

  constructor(
    private modalService: NgbModal,
    private notificationSubscriber: ServerNotificationSubscriberService,
    private auth: Authentication,
    private progressBar: ProgressBarService,
    private groupState: GroupRepositoryService,
  ) { }

  ngOnInit(): void {
    this.logEventsGroupPayments = [];
    this.paymentStatus = GroupPaymentStatus.PENDING;

    this.onAllGroupPaymentDone();
    this.onMemberPaid();
    this.onErrorWhileMemberPaying();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }

  private onErrorWhileMemberPaying(): void {
    this.notificationSubscriber.subscribe<ErrorWhilePayingToGroupAdmin>('group-payment-error-paying-admin', res => {

      if(this.isLoggedUserAdminOfCurrentGroup())
        this.logEventsGroupPayments.push({error: true, body: `${this.usernameFromUserId(res.userId)} couldnt be charged. Reason: ${res.errorMessage}`});

      if(res.groupId == this.auth.getUserId()){
        this.logEventsGroupPayments.push({error: true, body: `You couldnt be charged. Reason: ${res.errorMessage}`});
        this.paymentStatus = GroupPaymentStatus.ERROR;
      }
    });
  }

  private onMemberPaid(): void {
    this.notificationSubscriber.subscribe<MemberPaidToAdmin>('group-payment-member-paid-admin', res => {
      if(this.isLoggedUserAdminOfCurrentGroup()){
        this.logEventsGroupPayments.push({error: false, body: `You recieved the payment ${res.money} from ${this.usernameFromUserId(res.userId)}`});
      }

      if(res.userId == this.auth.getUserId()){
        this.logEventsGroupPayments.push({error: false, body: `You have been charged ${res.money}`});
      }
    });
  }

  private onAllGroupPaymentDone(): void {
    this.notificationSubscriber.subscribe<GroupPaymentDone>("group-payment-all-done", res => {
      this.progressBar.isLoading.next(false);
      this.paymentStatus = GroupPaymentStatus.DONE;
    });
  }

  private isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }

  private usernameFromUserId(userId: string): string{
    return this.groupState.getGroupMemberUserByUserId(userId)
      .username;
  }
}
