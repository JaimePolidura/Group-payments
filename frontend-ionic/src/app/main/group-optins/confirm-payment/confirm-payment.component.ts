import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {GroupCacheService} from "../../../../shared/groups/group-cache.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";
import {CommissionService} from "../../../../shared/payments/commission.service";

@Component({
  selector: 'app-confirm-payment',
  templateUrl: './confirm-payment.component.html',
  styleUrls: ['./confirm-payment.component.css']
})
export class ConfirmPaymentComponent implements OnInit {
  @Output() public paymentConfirmed: EventEmitter<void> = new EventEmitter<void>();

  constructor(
    private modalService: NgbModal,
    public groupState: GroupCacheService,
    public auth: AuthenticationCacheService,
    private commissionService: CommissionService,
  ) {}

  ngOnInit(): void {
  }

  public closeModal(): void{
    this.modalService.dismissAll();
  }

  public confirmPayment(): void {
    this.paymentConfirmed.emit();
  }

  public deductFromFee(money: number): number {
    return this.commissionService.deductFromFee(money);
  }
}
