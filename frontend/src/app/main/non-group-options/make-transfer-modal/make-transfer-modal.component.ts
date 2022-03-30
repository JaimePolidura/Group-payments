import {Component, OnInit} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersService} from "../../../../backend/users/users.service";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {PaymentsService} from "../../../../backend/payments/payments.service";
import {MakeTransferRquest} from "../../../../backend/payments/request/make-transfer-rquest";
import {ProgressBarService} from "../../../progress-bar.service";
import {
  ServerNotificationSubscriberService
} from "../../../../backend/notificatinos/server-notification-subscriber.service";
import {TransferDone} from "../../../../backend/notificatinos/notifications/transfer-done";
import {TransferRolledBack} from "../../../../backend/notificatinos/notifications/transfer-rolled-back";
import {TransferStatus} from "./transfer-status";
import {
  TransferFatalErrorRollingback
} from "../../../../backend/notificatinos/notifications/transfer-fatal-error-rollingback";

@Component({
  selector: 'app-make-transfer-modal',
  templateUrl: './make-transfer-modal.component.html',
  styleUrls: ['./make-transfer-modal.component.css']
})
export class MakeTransferModalComponent implements OnInit {
  public makeTransferForm: FormGroup;
  public userToMakeDesitnationFound: boolean;
  public userIdToMakeTransfer: string;
  public transferRolledbackSuccessfuly: boolean;
  public errorMessage: string;

  public transferStatus: TransferStatus;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private usersService: UsersService,
    private auth: Authentication,
    private paymentService: PaymentsService,
    public progressBar: ProgressBarService,
    private serverNotificationSubscriber: ServerNotificationSubscriberService,
  ){}

  ngOnInit(): void {
    this.userToMakeDesitnationFound = false;
    this.transferRolledbackSuccessfuly = false;

    this.transferStatus = TransferStatus.NOT_SENDED;

    this.setupTransferForm();
    this.registerNotificationsListener();
  }

  public setupTransferForm(): void {
    this.makeTransferForm = new FormGroup({
      to: new FormControl('', [Validators.required, Validators.email]),
      money: new FormControl(0, [Validators.min(0.01), Validators.required]),
      description: new FormControl('Transference', [Validators.required, Validators.minLength(1), Validators.maxLength(16)])
    });
  }
  get to(): AbstractControl { return <AbstractControl>this.makeTransferForm.get('to'); }
  get money(): AbstractControl { return <AbstractControl>this.makeTransferForm.get('money'); }
  get description(): AbstractControl { return <AbstractControl>this.makeTransferForm.get('description'); }

  public closeModal(): void {
    this.activeModal.dismiss();
  }

  public makeTransfer(): void {
    const req: MakeTransferRquest = {
      to: this.userIdToMakeTransfer,
      money: this.money.value,
      description: this.description.value,
    }

    this.progressBar.isLoading.next(true);
    this.transferStatus = TransferStatus.PENDING;

    this.paymentService.makeTransfer(req).subscribe(res => {});
  }

  public onKeyupEmailsDestinationCheckNotHisSelf(): void {
    if(!this.to.valid || !this.to.value) return;

    const emailsDestination: string = this.to.value;

    if(emailsDestination == this.auth.getEmail()){
      this.to.setErrors({cannotBeTheSame: true});
      return;
    }else{
      this.to.setErrors(null);
    }

    this.usersService.getUserIdByEmail(emailsDestination).subscribe(res => {
      this.to.setErrors(null);
      this.userToMakeDesitnationFound = true;
      this.userIdToMakeTransfer = res.userId;

    }, err => {
      this.to.setErrors({userNotFound: true});
      this.userToMakeDesitnationFound = false;
    });
  }

  private registerNotificationsListener() {
    this.onTransferDone();
    this.onTransferRolledback();
    this.onErrorRollingback();
  }

  private onTransferRolledback(): void {
    this.serverNotificationSubscriber.subscribe<TransferRolledBack>('transference-rolledback', res => {
      this.progressBar.isLoading.next(false);
      this.transferStatus = TransferStatus.ROLLED_BACK;
      this.errorMessage = res.reasonOfRollingback
    });
  }

  private onErrorRollingback(): void {
    this.serverNotificationSubscriber.subscribe<TransferFatalErrorRollingback>('transference-rollingback-error', res => {
      this.errorMessage = res.errorMessage;
      this.transferStatus = TransferStatus.ERROR_ROLLINGBACK;
      this.progressBar.isLoading.next(false);
    });
  }

  private onTransferDone(): void {
    this.serverNotificationSubscriber.subscribe<TransferDone>('transfer-done', res => {
      this.progressBar.isLoading.next(false);
      this.transferStatus = TransferStatus.SUCCESS;
    });
  }
}
