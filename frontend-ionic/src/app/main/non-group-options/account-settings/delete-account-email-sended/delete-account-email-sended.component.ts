import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
  ServerNotificationSubscriberService
} from "../../../../../shared/notificatinos/online/server-notification-subscriber.service";

@Component({
  selector: 'app-delete-account-email-sended',
  templateUrl: './delete-account-email-sended.component.html',
  styleUrls: ['./delete-account-email-sended.component.css']
})
export class DeleteAccountEmailSendedComponent implements OnInit {
  @Output() private emailResend: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private modalService: NgbModal,
    private notificationSubscriber: ServerNotificationSubscriberService,
  ) { }

  ngOnInit(): void {
    this.onAccountDeleted();
  }

  public closeModal(): void{
    this.modalService.dismissAll();
  }

  resnedEmail() {
    this.emailResend.emit();
  }

  private onAccountDeleted(): void {
    this.notificationSubscriber.subscribe('user-deleted', n => {
      this.closeModal();
    });
  }
}
