import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersApiService} from "../../../../shared/users/users-api.service";
import {PaymentsApiService} from "../../../../shared/payments/payments-api.service";
import {MakeTransferRquest} from "../../../../shared/payments/api/request/make-transfer-rquest";
import {ProgressBarService} from "../../../_shared/loading-progress-bar/progress-bar.service";
import {
  ServerNotificationSubscriberService
} from "../../../../shared/notificatinos/online/server-notification-subscriber.service";
import {TransferStatus} from "./transfer-status";
import {TransferDone} from 'src/shared/payments/notifications/transfer-done';
import {ErrorWhileMakingTransfer} from "../../../../shared/payments/notifications/error-while-making-transfer";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {RecentUser} from "../../../../shared/recentusers/model/recent-user";
import {RecentUsersService} from "../../../../shared/recentusers/recent-users.service";

const DEFAULT_TRANSFERENCE_DESCRIPCION = "This is a transference";

@Component({
  selector: 'app-make-transfer-modal',
  templateUrl: './make-transfer-modal.component.html',
  styleUrls: ['./make-transfer-modal.component.css']
})
export class MakeTransferModalComponent implements OnInit, AfterViewInit {
  public makeTransferForm: FormGroup;
  public userIdToMakeTransfer: string;
  public errorMessage: string;
  public transferStatus: TransferStatus;

  @ViewChild('makeTransferMoneyInput') public makeTransferMoneyInput: ElementRef<HTMLInputElement>;
  @ViewChild('makeTransferEmailDestinationInput') public makeTransferEmailDestinationInput: ElementRef<HTMLInputElement>;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private usersService: UsersApiService,
    private auth: AuthenticationCacheService,
    private paymentService: PaymentsApiService,
    public progressBar: ProgressBarService,
    private serverNotificationSubscriber: ServerNotificationSubscriberService,
    private recentUsers: RecentUsersService,
  ){}

  ngOnInit(): void {
    this.transferStatus = TransferStatus.NOT_SENDED;

    this.setupTransferForm();
    this.registerNotificationsListener();
  }

  ngAfterViewInit(): void{
    setTimeout(() => {
      this.makeTransferEmailDestinationInput.nativeElement.focus();
    }, 0);
  }

  public setupTransferForm(): void {
    this.makeTransferForm = new FormGroup({
      to: new FormControl('', [Validators.required, Validators.email]),
      money: new FormControl(undefined, [Validators.min(0.01), Validators.required]),
      description: new FormControl(DEFAULT_TRANSFERENCE_DESCRIPCION, [Validators.required, Validators.minLength(1), Validators.maxLength(24)])
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

    this.progressBar.start();
    this.transferStatus = TransferStatus.PENDING;

    this.paymentService.makeTransfer(req).subscribe(res => {}, err => {
      this.onError('User not found');
      this.recentUsers.deleteById(this.userIdToMakeTransfer);
    });
  }

  public onKeyUpInEmailsDestination(): void {
    if(!this.to.valid || !this.to.value) return;

    const emailsDestination: string = this.to.value;

    if(emailsDestination == this.auth.getEmail()){
      this.to.setErrors({cannotBeTheSame: true});
      return;
    }else if(this.recentUsers.findByEmail(emailsDestination) != undefined){
      this.to.setErrors(null);
      this.userIdToMakeTransfer = (<RecentUser> this.recentUsers.findByEmail(emailsDestination)).userId;
      return;
    }else{
      this.to.setErrors(null);
    }

    this.usersService.getUserIdByEmail(emailsDestination).subscribe(res => {
      this.to.setErrors(null);
      this.userIdToMakeTransfer = res.userId;
      this.makeTransferMoneyInput.nativeElement.focus();
    }, err => {
      this.to.setErrors({userNotFound: true});
    });
  }

  private registerNotificationsListener() {
    this.onTransferDone();
    this.onTransferError();
  }

  private onTransferError(): void {
    this.serverNotificationSubscriber.subscribe<ErrorWhileMakingTransfer>('transfer-error', res => {
      this.onError(res.errorCause);
    });
  }

  private onTransferDone(): void {
    this.serverNotificationSubscriber.subscribe<TransferDone>('transfer-done', res => {
      this.progressBar.stop();
      this.transferStatus = TransferStatus.SUCCESS;
    });
  }

  private onError(errorCause: string): void {
    this.progressBar.stop();
    this.transferStatus = TransferStatus.ERROR;
    this.errorMessage = errorCause;
  }

  public userToMakeTransferSelected(user: RecentUser) {
    this.to.setErrors(null);
    this.to.setValue(user.email);
    this.userIdToMakeTransfer = user.userId;
    this.makeTransferMoneyInput.nativeElement.focus();
  }
}
