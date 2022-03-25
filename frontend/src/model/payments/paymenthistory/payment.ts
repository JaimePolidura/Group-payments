import {PaymentState} from "./payment-state";
import {PaymentType} from "./payment-type";

export interface Payment {
  paymentId: string,
  date: string,
  payer: string,
  paid: string,
  money: number,
  description: string,
  state: PaymentState,
  type: PaymentType,
  errorMessage?: string,
}
