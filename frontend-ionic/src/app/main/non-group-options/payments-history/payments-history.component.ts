import {Component, OnInit} from '@angular/core';
import {PaymentsApiService} from "../../../../shared/payments/payments-api.service";
import {Payment} from "../../../../shared/payments/model/paymenthistory/payment";
import {PaymentTypeSearch} from "./payment-type-search";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {PaymentInfoModalComponent} from "./payment-info-modal/payment-info-modal.component";
import {AuthenticationCacheService} from "../../../../shared/auth/authentication-cache.service";

const ITEMS_PER_PAGE: number = 15;

@Component({
  selector: 'app-payments-history',
  templateUrl: './payments-history.component.html',
  styleUrls: ['./payments-history.component.css'],
})
export class PaymentsHistoryComponent implements OnInit {
  public paymentsPages: {[pageNumber: number]: Payment[]} = {};
  public actualPage: number = 0;
  private paymentTypeSearch: PaymentTypeSearch = PaymentTypeSearch.ALL;

  constructor(
    private paymentsService: PaymentsApiService,
    public auth: AuthenticationCacheService,
    public modalService: NgbModal,
  ){}

  ngOnInit(): void {
    this.getPayments(this.actualPage + 1, this.paymentTypeSearch, () => {});
  }

  private getPayments(newPageNumber: number, paymentTypeSearch: PaymentTypeSearch, onDataRecievedCallback: () => void): void {
    this.paymentsService.getPaymentHistory({pageNumber: newPageNumber,
                                                paymentTypeSearch: paymentTypeSearch,
                                                itemsPerPage: ITEMS_PER_PAGE
    }).subscribe(res => {

      onDataRecievedCallback();

      this.actualPage++;

      this.paymentsPages[this.actualPage] = res.payments;
    });
  }

  public onPaymentSearchTypeChanged(e: any) {
    const newTypeSearch: PaymentTypeSearch = e.target.value;

    if(newTypeSearch == this.paymentTypeSearch) return;

    this.getPayments( 1, newTypeSearch, () => {
      this.paymentTypeSearch = newTypeSearch;
      this.paymentsPages = {};
      this.actualPage = 0;
    });
  }

  public roundNumber(number: number): number {
    return Math.round(number);
  }

  public openModalPaymentInfo(payment: Payment): void {
    const modalRef = this.modalService.open(PaymentInfoModalComponent);
    modalRef.componentInstance.payment = payment;
  }

  public canGoForwardInPage(): boolean{
    return this.paymentsPages[this.actualPage] != undefined && this.paymentsPages[this.actualPage] != [] &&
      ITEMS_PER_PAGE <= this.paymentsPages[this.actualPage].length;
  }

  public canGoBackwardInPage(): boolean {
    return this.actualPage > 1;
  }

  public goBackwardInPage(): void {
    if (!this.canGoBackwardInPage()) return;

    this.actualPage--;
  }

  public goForwardInPage() {
    if(!this.canGoForwardInPage()) return;

    let newPageNumberToGoForward = this.actualPage + 1;

    if(this.hasPaymentPageLoaded(newPageNumberToGoForward))
      this.actualPage++;
    else
      this.getPayments(newPageNumberToGoForward, this.paymentTypeSearch, () => {});
  }

  private hasPaymentPageLoaded(pageNumber: number): boolean {
    return this.paymentsPages[pageNumber] != undefined;
  }

  public isPayer(payment: Payment): boolean {
    return payment.fromUserId == this.auth.getUserId();
  }

  public isPaid(payment: Payment): boolean {
    return payment.toUserId == this.auth.getUserId();
  }
}
