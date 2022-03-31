import { Component, OnInit } from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  ServerNotificationSubscriberService
} from "../../../../backend/notificatinos/server-notification-subscriber.service";
import {MemberPayingAppDone} from "../../../../backend/notificatinos/notifications/member-paying-app-done";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {ErrorWhileMemberPaying} from "../../../../backend/notificatinos/notifications/error-while-member-paying";
import {AppPayingAdminDone} from "../../../../backend/notificatinos/notifications/app-paying-admin-done";
import {ErrorWhilePayingToAdmin} from "../../../../backend/notificatinos/notifications/error-while-paying-to-admin";
import {PaymentDone} from "../../../../backend/notificatinos/notifications/payment-done";
import {ProgressBarService} from "../../../progress-bar.service";
import {GroupState} from "../../../../model/group/group-state";
import {User} from "../../../../model/user/user";
import {GroupRepositoryService} from "../../group-repository.service";

@Component({
  selector: 'app-group-payment',
  templateUrl: './group-payment.component.html',
  styleUrls: ['./group-payment.component.css']
})
export class GroupPaymentComponent implements OnInit {
  public donePaying: boolean;
  public donePayingWithoutErrors: boolean;
  public logEventsGroupPayments: {body: string, error: boolean}[];

  constructor(
    private modalService: NgbModal,
    private notificationSubscriber: ServerNotificationSubscriberService,
    private auth: Authentication,
    private progressBar: ProgressBarService,
    private groupState: GroupRepositoryService,
  ) { }

  ngOnInit(): void {
    this.donePayingWithoutErrors = false;
    this.donePaying = false;
    this.logEventsGroupPayments = [];

    this.onAllGroupPaymentDone();
    this.onMemberPaidToApp();
    this.onAppPayingAdmin();
    this.onErrorWhilePayingToAdmin();
    this.onErrorWhileMemberPaying();
  }

  public closeModal(): void {
    this.modalService.dismissAll();
  }


  private onMemberPaidToApp(): void {
    this.notificationSubscriber.subscribe<MemberPayingAppDone>('group-payment-member-app-done', res => {

      if(this.auth.getUserId() == res.groupMemberUserId){
        this.logEventsGroupPayments.push({error: false, body: `You have been charged ${res.money}${this.auth.getCurrency().symbol}`});
        this.donePayingWithoutErrors = true;
      }
    });
  }

  private onErrorWhileMemberPaying(): void {
    this.notificationSubscriber.subscribe<ErrorWhileMemberPaying>('group-payment-error-member-paying', res => {
      const username = this.groupState.getGroupMemberUserByUserId(res.groupId).username;

      if(this.isLoggedUserAdminOfCurrentGroup())
        this.logEventsGroupPayments.push({error: true, body: `${username} couldnt be charged. Reason: ${res.errorMessage}`});

      if(res.groupMemberUserId == this.auth.getUserId())
        this.logEventsGroupPayments.push({error: true, body: `You couldnt be charged. Reason: ${res.errorMessage}`});
    });
  }

  private onAppPayingAdmin(): void {
    this.notificationSubscriber.subscribe<AppPayingAdminDone>('group-payment-app-admin-done', res => {
      if(this.isLoggedUserAdminOfCurrentGroup()){
        this.logEventsGroupPayments.push({error: false, body: `You recieved the payment ${res.money}`});
        this.donePayingWithoutErrors = true;
      }
    });
  }

  private onErrorWhilePayingToAdmin(): void {
    this.notificationSubscriber.subscribe<ErrorWhilePayingToAdmin>('group-payment-error-paying-admin', res => {
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
    this.notificationSubscriber.subscribe<PaymentDone>("group-payment-all-done", res => {
      this.donePaying = true;
      this.progressBar.isLoading.next(false);
    });
  }

  private isLoggedUserAdminOfCurrentGroup(): boolean {
    return this.groupState.isAdminOfCurrentGroup(this.auth.getUserId());
  }
}
