import { Component, OnInit } from '@angular/core';
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-make-transfer-modal',
  templateUrl: './make-transfer-modal.component.html',
  styleUrls: ['./make-transfer-modal.component.css']
})
export class MakeTransferModalComponent implements OnInit {
  public makeTransferForm: FormGroup;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal
  ){}

  ngOnInit(): void {
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

  }

  public onKeyupEmailsDestinationCheckNotHisSelf(): void {
    if(!this.to.valid || !this.to.value) return;

    const emailsDestination: string = this.to.value;

  }
}
