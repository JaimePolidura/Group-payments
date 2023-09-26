import { Injectable } from '@angular/core';
import {ServerNotificationSubscriberService} from "../notificatinos/online/server-notification-subscriber.service";
import {InvitationRejected} from "./notifications/invitation-rejected";
import {GroupCacheService} from "../groups/group-cache.service";
import {ToastService} from "../../app/_shared/toast/toast-service";
import {UsersApiService} from "../users/users-api.service";

@Injectable({
  providedIn: 'root'
})
export class OnInvitationRejectedService {

  constructor(private serverNotificationSubscriber: ServerNotificationSubscriberService,
              private groupCache: GroupCacheService,
              private toastService: ToastService,
              private userService: UsersApiService) {

    this.serverNotificationSubscriber.subscribe<InvitationRejected>('group-invitations-rejected', async res => {
      await this.on(res);
    });
  }

  private async on(invitationRejected: InvitationRejected) {
    const notInGroupOfNotification : boolean = this.groupCache.getGroup() == undefined ||
      this.groupCache.getGroup().groupId != invitationRejected.groupId;

    if(notInGroupOfNotification)
      return;

    const username: string = (await this.userService.getUserDataByUserIdId(invitationRejected.toUserId)
      .toPromise())
      ?.username;

    this.toastService.error(
      `${username} rejected your invitation to the group`,``
    );
  }
}
