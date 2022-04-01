import {PaymentState} from "./payment-state";
import {PaymentContext} from "./payment-context";

export interface Payment {
  paymentId: string,
  date: string,
  fromUserId: string,
  toUserId: string,
  money: number,
  description: string,
  state: PaymentState,
  context: PaymentContext,
  errorMessage?: string,
}
