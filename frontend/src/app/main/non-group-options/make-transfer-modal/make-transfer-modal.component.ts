import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {UsersService} from "../../../../backend/users/users.service";
import {Authentication} from "../../../../backend/users/authentication/authentication";
import {PaymentsService} from "../../../../backend/payments/payments.service";
import {MakeTransferRquest} from "../../../../backend/payments/request/make-transfer-rquest";

@Component({
  selector: 'app-make-transfer-modal',
  templateUrl: './make-transfer-modal.component.html',
  styleUrls: ['./make-transfer-modal.component.css']
})
export class MakeTransferModalComponent implements OnInit {
  public makeTransferForm: FormGroup;
  public userToMakeDesitnationFound: boolean;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal,
    private usersService: UsersService,
    private auth: Authentication,
    private paymentService: PaymentsService,
  ){}

  ngOnInit(): void {
    this.userToMakeDesitnationFound = false;
    this.setupTransferForm();
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
      to: this.to.value,
      money: this.money.value,
      description: this.description.value,
    }

    this.paymentService.makeTransfer(req).subscribe(res => {

    });
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

    this.usersService.existsByEmail(emailsDestination).subscribe(res => {
      console.log(res);

      if(res.exists && emailsDestination != this.auth.getUserId()){
        this.to.setErrors(null);
        this.userToMakeDesitnationFound = true;
      }else{
        this.to.setErrors({userNotFound: true});
        this.userToMakeDesitnationFound = false;
      }
    });
  }
}
