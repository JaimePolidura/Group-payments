import { Component, OnInit } from '@angular/core';
import {PaymentsService} from "../../../../backend/payments/payments.service";

@Component({
  selector: 'app-payments-history',
  templateUrl: './payments-history.component.html',
  styleUrls: ['./payments-history.component.css']
})
export class PaymentsHistoryComponent implements OnInit {

  constructor(
    private paymentsService: PaymentsService,
  ){}

  ngOnInit(): void {
    this.paymentsService.getPaymentHistory({pageNumber: 2, paymentTypeSearch: "ALL", itemsPerPage: 10}).subscribe(res => {
      console.log(res);
    });
  }

}
