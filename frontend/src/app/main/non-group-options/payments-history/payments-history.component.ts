import {Component, OnInit} from '@angular/core';
import {PaymentsService} from "../../../../backend/payments/payments.service";
import {Payment} from "../../../../model/payments/paymenthistory/payment";
import {PaymentTypeSearch} from "./payment-type-search";
import {Authentication} from "../../../../backend/authentication/authentication";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PaymentInfoModalComponent} from "./payment-info-modal/payment-info-modal.component";

const ITEMS_PER_PAGE: number = 15;

@Component({
  selector: 'app-payments-history',
  templateUrl: './payments-history.component.html',
  styleUrls: ['./payments-history.component.css']
})
export class PaymentsHistoryComponent implements OnInit {
  public paymentsWithItsPage: {[pageNumber: number]: Payment[]} = {};
  public actualPage: number = 0;
  private paymentTypeSearch: PaymentTypeSearch = PaymentTypeSearch.ALL;

  constructor(
    private paymentsService: PaymentsService,
    public auth: Authentication,
    public modalService: NgbModal,
  ){}

  ngOnInit(): void {
    this.getPayments(this.actualPage + 1, this.paymentTypeSearch);
  }

  private getPayments(newPageNumber: number, paymentTypeSearch: PaymentTypeSearch): void {
    this.paymentsService.getPaymentHistory({pageNumber: newPageNumber,
                                                paymentTypeSearch: paymentTypeSearch,
                                                itemsPerPage: ITEMS_PER_PAGE
    }).subscribe(res => {
      this.actualPage++;

      this.paymentsWithItsPage[this.actualPage] = res.payments;
    });
  }

  public onPaymentSearchTypeChanged(e: any) {
    //TODO
    // const newTypeSearch: PaymentTypeSearch = e.target.value;
  }

  public roundNumber(number: number): number {
    return Math.round(number);
  }

  public openModalPaymentInfo(payment: Payment): void {
    const modalRef = this.modalService.open(PaymentInfoModalComponent);
    modalRef.componentInstance.payment = payment;
  }
}
