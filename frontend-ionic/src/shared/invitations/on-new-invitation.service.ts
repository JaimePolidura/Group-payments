import { Injectable } from '@angular/core';
import {InvitationCreated} from "./notifications/invitation-created";
import {GetUserDataByUserId} from "../users/api/response/get-user-data-by-user-id";
import {ServerNotificationSubscriberService} from "../notificatinos/online/server-notification-subscriber.service";
import {InvitationsCacheService} from "./invitations-cache.service";
import {UsersApiService} from "../users/users-api.service";
import {ToastService} from "../../app/_shared/toast/toast-service";
import {ProgressBarService} from "../../app/_shared/loading-progress-bar/progress-bar.service";
import {InvitationsApiService} from "./invitations-api.service";

@Injectable({
  providedIn: 'root'
})
export class OnNewInvitationService {

  constructor(
    private serverNotificationSubscriber: ServerNotificationSubscriberService,
    private invitationCache: InvitationsCacheService,
    private usersService: UsersApiService,
    private toastService: ToastService,
    private progressbar: ProgressBarService,
    private invitationService: InvitationsApiService,
  ) {

    this.serverNotificationSubscriber.subscribe<InvitationCreated>('group-invitations-created', async notification => {
      await this.on(notification);
    });
  }

  private async on(newInvitationEvent: InvitationCreated) {
    this.invitationCache.add({
      invitationId: newInvitationEvent.invitationId,
      groupId: newInvitationEvent.groupId,
      fromUserId: newInvitationEvent.fromUserId,
      toUserId: newInvitationEvent.toUserId,
      date: newInvitationEvent.date,
      description: newInvitationEvent.description,
    })

    const username: string = (<GetUserDataByUserId> await this.usersService.getUserDataByUserIdId(newInvitationEvent.fromUserId)
      .toPromise())
      .username;

    this.toastService.info(
      `${username} has invited you to a group, ${newInvitationEvent.description} ,click here to accept`,
      `${username} invited you`, () => {
        this.progressbar.start();

        this.invitationService.accpet({invitationId: newInvitationEvent.invitationId}).subscribe(res => {
          this.progressbar.stop();
        }, err => {
          this.invitationCache.deleteById(newInvitationEvent.invitationId);
          console.log(err);
          window.alert("Cannot join. The group migh have been removed or started paying");
        });
      }
    );
  }
}
