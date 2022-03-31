import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {GroupRepositoryService} from "../../group-repository.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Authentication} from "../../../../backend/users/authentication/authentication";

@Component({
  selector: 'app-confirm-payment',
  templateUrl: './confirm-payment.component.html',
  styleUrls: ['./confirm-payment.component.css']
})
export class ConfirmPaymentComponent implements OnInit {
  @Output() public paymentConfirmed: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private modalService: NgbModal,
    public groupState: GroupRepositoryService,
    public auth: Authentication,
  ) {}

  ngOnInit(): void {
  }

  public closeModal(): void{
    this.modalService.dismissAll();
  }

  public confirmPayment(): void {
    this.paymentConfirmed.emit();
  }

}
