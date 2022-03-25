import {Component, Input, OnInit} from '@angular/core';
import {Payment} from "../../../../../model/payments/paymenthistory/payment";
import {NgbActiveModal, NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Authentication} from "../../../../../backend/authentication/authentication";

@Component({
  selector: 'app-payment-info-modal',
  templateUrl: './payment-info-modal.component.html',
  styleUrls: ['./payment-info-modal.component.css']
})
export class PaymentInfoModalComponent implements OnInit {
  @Input() payment: Payment;

  constructor(
    public modalService: NgbModal,
    public activeModal: NgbActiveModal,
    public auth: Authentication,
  ){ }

  ngOnInit(): void {
  }

  public roundNumber(number: number): number {
    return Math.round(number);
  }

}