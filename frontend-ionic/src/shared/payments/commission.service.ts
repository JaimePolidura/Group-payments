import { Injectable } from '@angular/core';
import {PaymentsApiService} from "./payments-api.service";

@Injectable({
  providedIn: 'root'
})
export class CommissionService {
  private commission: number;

  constructor(private paymentsApiService: PaymentsApiService,) {
    this.commission = 0;
    this.loadCommissionPolicy();
  }

  private loadCommissionPolicy(): void {
    this.paymentsApiService.getCommissionPolicy().subscribe(response => {
      this.commission = response.commission;
    });
  }

  public deductFromFee(money: number): number {
    return money - this.collecteFee(money);
  }

  public collecteFee(money: number): number {
    return (this.commission / 100) * money;
  }
}
