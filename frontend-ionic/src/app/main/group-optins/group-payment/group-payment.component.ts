import {Component, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  ServerNotificationSubscriberService
} from "../../../../shared/notificatinos/online/server-notification-subscriber.service";
import {GroupPaymentDone} from "../../../../shared/groups/notifications/group-payment-done";
import {ProgressBarService} from "../../../_shared/loading-progress-bar/progress-bar.service";
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {MemberPaidToAdmin} from 'src/shared/groups/notifications/member-paid-to-admin';
import {
  ErrorWhilePayingToGroupAdmin
} from 'src/shared/groups/notifications/error-while-paying-to-group-admin';
import {GroupPaymentStatus} from "./group-payment-status";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";

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
    private auth: AuthenticationCacheService,
    private progressBar: ProgressBarService,
    private groupState: GroupCacheService,
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
      if(this.isLoggedUserAdminOfCurrentGroup())
        this.logEventsGroupPayments.push({error: false, body: `You recieved the payment ${res.money} ${this.auth.getCurrency().symbol} from ${this.usernameFromUserId(res.userId)}`});

      if(res.userId == this.auth.getUserId())
        this.logEventsGroupPayments.push({error: false, body: `You have been charged ${res.money} ${this.groupState.getCurrency().symbol}`});
    });
  }

  private onAllGroupPaymentDone(): void {
    this.notificationSubscriber.subscribe<GroupPaymentDone>("group-payment-all-done", res => {
      this.progressBar.stop();
      this.paymentStatus = GroupPaymentStatus.DONE;
    });
  }

  private isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }

  private usernameFromUserId(userId: string): string{
    return this.groupState.getMemberByUserId(userId)
      .username;
  }
}
